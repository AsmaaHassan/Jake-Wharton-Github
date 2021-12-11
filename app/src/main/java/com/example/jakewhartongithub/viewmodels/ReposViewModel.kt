 package com.example.jakewhartongithub.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.jakewhartongithub.data.RepoRemoteMediator
import com.example.jakewhartongithub.data.internal.RepoDatabase
import com.example.jakewhartongithub.data.remote.ServiceApi
import com.example.jakewhartongithub.models.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
 /**
  * Created by Asmaa Hassan
  */
class ReposViewModel(private val db:RepoDatabase,private val retrofit: Retrofit,ctx: Context) : ViewModel() {

    @ExperimentalPagingApi
    val jakeWhortonRepos: Flow<PagingData<Repo>> = Pager(
        config = PagingConfig(pageSize = 15,enablePlaceholders = false),
        pagingSourceFactory = {db.getRepoDao.getAllRepos()},
        remoteMediator = RepoRemoteMediator(db,retrofit = retrofit)
    ).flow.cachedIn(viewModelScope)
}


