package com.example.jakewhartongithub

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import com.example.jakewhartongithub.data.internal.RepoDatabase

import com.example.jakewhartongithub.data.remote.RetrofitProvider
import com.example.jakewhartongithub.data.remote.ServiceApi
import com.example.jakewhartongithub.viewmodels.ReposViewModelFactory
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import java.util.Collections.singleton

open class JakeWhortonApp : Application(), KodeinAware, LifecycleObserver {

    private val retrofitModule = Kodein.Module(name = "Retrofit") {
        bind<Retrofit>() with singleton { RetrofitProvider.getInstance() }
    }

    private val reposModule = Kodein.Module(name = "Repos") {
        bind<RepoDatabase>() with  singleton { RepoDatabase.getInstance(this@JakeWhortonApp) }
        bind<ReposViewModelFactory>() with provider { ReposViewModelFactory(instance(),instance(),this@JakeWhortonApp) }
    }

    override val kodein by Kodein.lazy {
        import(androidXModule(this@JakeWhortonApp))
        import(retrofitModule)
        import(reposModule)

    }
}