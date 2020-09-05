package com.android.practice.github_repository_search_kotlin_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.android.practice.github_repository_search_kotlin_mvvm.Repository.SharedRepository
import com.android.practice.github_repository_search_kotlin_mvvm.databinding.ActivityMainBinding
import com.android.practice.github_repository_search_kotlin_mvvm.viewModel.SharedViewModel

class MainActivity : AppCompatActivity() {

    val recyclerView: RecyclerView

    init {
        recyclerView = findViewById(R.id.recyclerView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        mainViewModel.runData()
        mainViewModel.toMain.observe(this, {

        })

        DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        ).apply {
            this.setLifecycleOwner(this@MainActivity)
            mMainViewModel = mainViewModel
        }


    }
}