package com.example.jakewhartongithub.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jakewhartongithub.data.internal.RepoDatabase
import com.example.jakewhartongithub.data.remote.ServiceApi
import retrofit2.Retrofit

/**
 * Created by Asmaa Hassan
 */

class ReposViewModelFactory(private val db: RepoDatabase, private val retrofit: Retrofit, private val ctx: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReposViewModel(db, retrofit =retrofit ,ctx) as T
    }
}