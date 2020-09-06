package com.android.practice.github_repository_search_kotlin_mvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.practice.github_repository_search_kotlin_mvvm.Model.Item
import com.android.practice.github_repository_search_kotlin_mvvm.Model.RepoModel
import com.android.practice.github_repository_search_kotlin_mvvm.Repository.SharedRepository

class SharedViewModel : ViewModel() {


    val sharedRepository = SharedRepository()
    //SharedRepository sharedRepository = new Shra...()

    fun runData() {
        sharedRepository.getPublicData()
    }



    //ToMain
    val toMain: LiveData<RepoModel>
        get() = sharedRepository.repository
}