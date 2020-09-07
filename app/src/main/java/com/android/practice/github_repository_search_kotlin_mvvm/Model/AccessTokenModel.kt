package com.android.practice.github_repository_search_kotlin_mvvm.Model


import com.google.gson.annotations.SerializedName

data class AccessTokenModel(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("refresh_token_expires_in")
    val refreshTokenExpiresIn: String,
    val scope: String,
    @SerializedName("token_type")
    val tokenType: String
)