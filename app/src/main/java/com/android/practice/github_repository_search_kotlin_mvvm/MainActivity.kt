package com.android.practice.github_repository_search_kotlin_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.practice.github_repository_search_kotlin_mvvm.Repository.SharedRepository
import com.android.practice.github_repository_search_kotlin_mvvm.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var txtResults: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewMain)
        txtResults = findViewById(R.id.heading_label)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        var recyclerViewAdapter: recyclerViewAdapter


        val mainViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        mainViewModel.runData()
        mainViewModel.toMain.observe(this, {
            recyclerViewAdapter = recyclerViewAdapter(this, it)
            recyclerView.adapter = recyclerViewAdapter
            heading_label.text = "Results: " + it.totalCount.toString()
        })


    }
}