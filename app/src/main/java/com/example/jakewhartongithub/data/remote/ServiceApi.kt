package com.example.jakewhartongithub.data.remote

import com.example.jakewhartongithub.models.Repo
import retrofit2.http.GET
import retrofit2.http.Query
/**
 * Created by Asmaa Hassan
 */
interface ServiceApi {
    @GET("/users/JakeWharton/repos")
    suspend fun getStarredReposApi(@Query("page") page: Int =1
        ,@Query("per_page") per_page:Int) : List<Repo>


}