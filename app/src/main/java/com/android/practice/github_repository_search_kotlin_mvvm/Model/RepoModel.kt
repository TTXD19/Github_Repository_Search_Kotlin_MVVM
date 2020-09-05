package com.android.practice.github_repository_search_kotlin_mvvm.Model


import com.google.gson.annotations.SerializedName

data class RepoModel(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    val items: List<Item>,
    @SerializedName("total_count")
    val totalCount: Int


)