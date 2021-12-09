 package com.example.jakewhartongithub.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jakewhartongithub.JakeWhortonApp
import com.example.jakewhartongithub.data.remote.RemoteDataSource
import com.example.jakewhartongithub.data.remote.RetrofitProvider
import com.example.jakewhartongithub.models.Repo
import kotlinx.coroutines.flow.Flow
import org.kodein.di.*
 /**
  * Created by Asmaa Hassan
  */
class ReposViewModel(ctx: Context) : ViewModel(), DIAware {
    override val di by lazy { (ctx as JakeWhortonApp).di }

    val jakeWhortonRepos: Flow<PagingData<Repo>> = Pager(PagingConfig(pageSize = 6)) {
        RemoteDataSource(RetrofitProvider.getInstance())
    }.flow.cachedIn(viewModelScope)
}


