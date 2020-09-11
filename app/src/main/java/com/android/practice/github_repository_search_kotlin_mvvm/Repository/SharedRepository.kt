package com.android.practice.github_repository_search_kotlin_mvvm.Repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.practice.github_repository_search_kotlin_mvvm.Model.AccessTokenModel
import com.android.practice.github_repository_search_kotlin_mvvm.Model.Item
import com.android.practice.github_repository_search_kotlin_mvvm.Model.RepoModel
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class SharedRepository {


    private val mRepositroy = MutableLiveData<RepoModel>()
    private val mPrivateRepo = MutableLiveData<List<Item>>()

    val repository: LiveData<RepoModel>
        get() = mRepositroy

    val privateRepo: LiveData<List<Item>>
        get() = mPrivateRepo

    //For MainActivity
    fun getPublicData(keyword: String, page: Int) {

        val publicRepo = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())

        val publicRetrofit = publicRepo.build()
        val gitAPI = publicRetrofit.create(GitJsonAPI::class.java)

        val call = gitAPI.getRepo(keyword, page, "stars", "desc")

        call.enqueue(object : Callback<RepoModel> {
            override fun onResponse(call: Call<RepoModel>, response: Response<RepoModel>) {
                if (response.isSuccessful) {
                    Log.d("Retrofit", "Response success")
                    /**
                     * postValue不care是不是在mainThread update
                     * .value不是在MainThread時會掛掉
                     */
                    //mRepositroy.postValue(response.body())
                    mRepositroy.value = response.body()
                } else {
                    Log.d("Retrofit", response.message().toString())
                }
            }

            override fun onFailure(call: Call<RepoModel>, t: Throwable) {
                Log.d("Retrofit", t.message.toString())
            }

        })
    }

    fun getAccessToken(clien_id: String, client_secret: String, code: String) {
        val loginConnectBuilder = Retrofit.Builder()
            .baseUrl("https://github.com/")
            .addConverterFactory(GsonConverterFactory.create())

        val loginRetro = loginConnectBuilder.build()
        val loginAPI = loginRetro.create(GitJsonAPI::class.java)
        val callAPI = loginAPI.requestAccessToken(clien_id, client_secret, code)
        callAPI.enqueue(object : Callback<AccessTokenModel> {
            override fun onResponse(
                call: Call<AccessTokenModel>,
                response: Response<AccessTokenModel>
            ) {
                if (response.isSuccessful) {
                    val tokenModel = response.body()
                    getPrivatUserRepo("token " + tokenModel!!.accessToken)
                    Log.d("token", tokenModel.accessToken)
                }
            }

            override fun onFailure(call: Call<AccessTokenModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    fun getPrivatUserRepo(access_token: String) {
        val loginConnectBuilder = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())

        val loginRetro = loginConnectBuilder.build()
        val loginAPI = loginRetro.create(GitJsonAPI::class.java)
        val callAPI = loginAPI.getUsersRepo(access_token)
        callAPI.enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    mPrivateRepo.value = response.body()
                }else{
                    Log.d("Message", response.message())
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                Log.d("MessageFail", t.message)
            }

        })
    }
}