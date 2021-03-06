package com.android.practice.github_repository_search_kotlin_mvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.practice.github_repository_search_kotlin_mvvm.Model.Item
import com.android.practice.github_repository_search_kotlin_mvvm.Model.RepoModel
import com.android.practice.github_repository_search_kotlin_mvvm.Repository.SharedRepository

class SharedViewModel : ViewModel() {


    val sharedRepository = SharedRepository()

    fun runData(keyword: String, page: Int) {
        sharedRepository.getPublicData(keyword, page)
    }

    fun getPrivateRepo(clien_id: String, client_secret: String, code: String){
        sharedRepository.getAccessToken(clien_id, client_secret, code)
    }

    //ToMain
    val toMain: LiveData<RepoModel>
        get() = sharedRepository.repository

    val toMainPrivateRepo: LiveData<List<Item>>
        get() = sharedRepository.privateRepo
}