package com.android.practice.github_repository_search_kotlin_mvvm.Repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.practice.github_repository_search_kotlin_mvvm.Model.Item
import com.android.practice.github_repository_search_kotlin_mvvm.Model.RepoModel
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class SharedRepository {



    private val mRepositroy = MutableLiveData<RepoModel>()

    val repository: LiveData<RepoModel>
        get() = mRepositroy


    fun getPublicData(){

        val publicRepo = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())

        val publicRetrofit = publicRepo.build()
        val gitAPI = publicRetrofit.create(GitJsonAPI::class.java)

        val call = gitAPI.getRepo("Android", 1, "stars", "desc")

        call.enqueue(object : Callback<RepoModel> {
            override fun onResponse(call: Call<RepoModel>, response: Response<RepoModel>) {
                if (response.isSuccessful) {
                    Log.d("TEST", "RUN")
                    /**
                     * postValue不care是不是在mainThread update
                     * .value不是在MainThread時會掛掉
                     */
                    //mRepositroy.postValue(response.body())
                    mRepositroy.value = response.body()
                }
            }

            override fun onFailure(call: Call<RepoModel>, t: Throwable) {
            }

        })
    }
}