package com.android.practice.github_repository_search_kotlin_mvvm.Repository

import com.android.practice.github_repository_search_kotlin_mvvm.Model.AccessTokenModel
import com.android.practice.github_repository_search_kotlin_mvvm.Model.Item
import com.android.practice.github_repository_search_kotlin_mvvm.Model.RepoModel
import retrofit2.Call
import retrofit2.http.*

interface GitJsonAPI {

    /**
     * Getting public data
     */
    @GET("search/repositories")
    fun getRepo(
        @Query("q") keyWord: String,
        @Query("page") page: Int,
        @Query("sort") sort: String,
        @Query("order") order: String
    ): Call<RepoModel>

    /**
     * Get private data's access token
     */
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    fun requestAccessToken(
        @Field("client_id") client_id: String,
        @Field("client_secret") client_secret: String,
        @Field("code") code: String,
    ): Call<AccessTokenModel>

    /**
     * Get user's private data
     */

    @GET("user/repos")
    fun getUsersRepo(
        @Query("access_token") accessToken: String
    ): Call<List<Item>>


}