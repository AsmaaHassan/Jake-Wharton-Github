package com.example.jakewhartongithub.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.jakewhartongithub.data.internal.RepoDatabase
import com.example.jakewhartongithub.data.remote.ServiceApi
import com.example.jakewhartongithub.models.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException
/**
 * Created by Asmaa Hassan
 */

@ExperimentalPagingApi
class RepoRemoteMediator constructor(
    private val db:RepoDatabase,
    private val retrofit: Retrofit
)  : RemoteMediator<Int,Repo>() {
    private val serviceApi: ServiceApi = retrofit.create(ServiceApi::class.java)
    private val STARTING_PAGE_INDEX = 1

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Repo>): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page  = when(pageKeyData){
            is MediatorResult.Success ->{
                return pageKeyData
            }else->{
                pageKeyData as Int
            }
        }

        try {
            val response = serviceApi.getStarredReposApi(page,state.config.pageSize)
            val endOfList = response.isEmpty()
            db.withTransaction {
                if(loadType == LoadType.REFRESH){
                    db.remoteKeyDao.clearAll()
                    db.getRepoDao.clearAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page-1
                val nextKey = if(endOfList) null else page+1
                val keys = response.map {
                    it.id?.let { it1 -> RemoteKeys(it1,prevKey,nextKey) }
                }
                db.remoteKeyDao.insertRemote(keys as List<RemoteKeys>)
                db.getRepoDao.insert(response)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfList)
        }catch (e:IOException){
            return   MediatorResult.Error(e)
        }catch (e:HttpException){
            return MediatorResult.Error(e)
        }
    }


    private suspend fun getKeyPageData(loadType: LoadType,state: PagingState<Int, Repo>) : Any{
        return when(loadType){
            LoadType.REFRESH->{
                val remoteKeys = getRefreshRemoteKey(state)
                remoteKeys?.nextKey?.minus(1)?:STARTING_PAGE_INDEX
            }
            LoadType.PREPEND->{
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?:MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
            LoadType.APPEND->{
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey ?:MediatorResult.Success(
                    endOfPaginationReached = true
                )
                nextKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, Repo>) : RemoteKeys? {
        return withContext(Dispatchers.IO){
            state.pages
                .firstOrNull { it.data.isNotEmpty() }
                ?.data?.firstOrNull()
                ?.let { repo -> repo.id?.let { db.remoteKeyDao.getRemoteKeys(it) } }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Repo>) : RemoteKeys? {
        return withContext(Dispatchers.IO){
            state.pages
                .lastOrNull{it.data.isNotEmpty()}
                ?.data?.lastOrNull()
                ?.let { repo -> repo.id?.let { db.remoteKeyDao.getRemoteKeys(it) } }
        }
    }

    private suspend fun getRefreshRemoteKey(state: PagingState<Int, Repo>) : RemoteKeys? {
        return withContext(Dispatchers.IO){
            state.anchorPosition?.let { position->
                state.closestItemToPosition(position)?.id?.let {repId->
                    db.remoteKeyDao.getRemoteKeys(repId)
                }
            }
        }
    }

}
