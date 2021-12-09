package com.example.jakewhartongithub

import android.app.Application
import com.example.jakewhartongithub.data.remote.RemoteDataSource
import com.example.jakewhartongithub.data.remote.RetrofitProvider
import com.example.jakewhartongithub.viewmodels.ReposViewModelFactory
import org.kodein.di.*
import retrofit2.Retrofit

class JakeWhortonApp : Application() {
    override fun onCreate() {
        super.onCreate()

    }

    val di = DI {
        bind<Retrofit>() with singleton { RetrofitProvider.getInstance() }
        bind<RemoteDataSource>() with  singleton { RemoteDataSource(instance()) }
        bind<ReposViewModelFactory>() with provider { ReposViewModelFactory(this@JakeWhortonApp) }
    }
}