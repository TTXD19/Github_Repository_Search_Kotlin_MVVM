package com.android.practice.github_repository_search_kotlin_mvvm.PersonaActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.android.practice.github_repository_search_kotlin_mvvm.R
import com.android.practice.github_repository_search_kotlin_mvvm.Repository.SharedRepository
import com.android.practice.github_repository_search_kotlin_mvvm.viewModel.SharedViewModel

class PersonalActivity : AppCompatActivity() {

    lateinit var editEmail: EditText
    lateinit var btnLogin: Button

    private val client_id = "a381e2f9b598fa45fb3e"
    private val client_secret = "18f8ec73b7906980f3c989487c811ee55f7f5237"
    private val redirect_url = "kotlinloginmvvm://callback"
    val sharedRepo = SharedRepository()
    var shareViewModel = SharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_repository)

        btnLogin = findViewById(R.id.btnGitUserPasswordLogin)
        editEmail = findViewById(R.id.username)


        shareViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        shareViewModel.toMainPrivateRepo.observe(this, {
            val name = it.get(0).fullName
            Log.d("Personal", name)
        })


        btnLogin.setOnClickListener(View.OnClickListener {
            if (!editEmail.text.isEmpty()){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/login/oauth/authorize" + "?client_id=" + client_id + "&login=" + editEmail.text.toString() + "&scope=repo&redirect_uri=" + redirect_url))
                startActivity(intent)
            }
        })
    }


    override fun onResume() {
        super.onResume()
        val uri = intent.data
        if (uri != null){
            Log.d("Personal", uri.toString())
            val code = uri.getQueryParameter("code")
            shareViewModel.getPrivateRepo(client_id, client_secret, code!!)
        }
    }
}