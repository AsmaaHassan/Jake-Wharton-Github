package com.example.jakewhartongithub.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jakewhartongithub.models.Repo
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException
/**
 * Created by Asmaa Hassan
 */
class RemoteDataSource(retrofit: Retrofit) : PagingSource<Int, Repo>() {
    private val serviceApi: ServiceApi = retrofit.create(ServiceApi::class.java)

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        try {
            val nextPage = params.key ?: 1
            val userList = serviceApi.getStarredReposApi(page = nextPage)
            return LoadResult.Page(
                data = userList.items,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (userList.items.isEmpty()) null else nextPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}