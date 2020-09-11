package com.android.practice.github_repository_search_kotlin_mvvm.adapter

import com.android.practice.github_repository_search_kotlin_mvvm.Model.Item

interface MainOnClickListener {
    fun onClicked(position: Int, itemList: MutableList<Item?>)
}