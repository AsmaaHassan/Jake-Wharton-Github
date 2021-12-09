package com.example.jakewhartongithub.data.remote

import com.example.jakewhartongithub.utils.Constants.Companion.BASE_IMAGE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/**
 * Created by Asmaa Hassan
 */
object RetrofitProvider {

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
          .client(provideOkHttpClient(provideLoggingInterceptor()))
            .baseUrl(BASE_IMAGE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }


    private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val b = OkHttpClient.Builder()
        b.addInterceptor(interceptor)
        return b.build()
    }


}