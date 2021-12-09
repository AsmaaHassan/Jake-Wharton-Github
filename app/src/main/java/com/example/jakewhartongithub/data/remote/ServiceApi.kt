package com.example.jakewhartongithub.data.remote

import com.example.jakewhartongithub.models.ResponseRepoPaginated
import retrofit2.http.GET
import retrofit2.http.Query
/**
 * Created by Asmaa Hassan
 */
interface ServiceApi {
    @GET("/search/repositories?q=created:>2017-10-22&sort=stars&order=desc")
    suspend fun getStarredReposApi(@Query("page") page: Int =1) : ResponseRepoPaginated


}