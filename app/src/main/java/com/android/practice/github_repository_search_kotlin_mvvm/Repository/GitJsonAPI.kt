package com.android.practice.github_repository_search_kotlin_mvvm.Repository

import com.android.practice.github_repository_search_kotlin_mvvm.Model.RepoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GitJsonAPI {

    @GET("search/repositories")
    fun getRepo(
        @Query("q") keyWord:String,
        @Query("page") page:Int,
        @Query("sort") sort:String,
        @Query("order") order:String
    ): Call<RepoModel>


}