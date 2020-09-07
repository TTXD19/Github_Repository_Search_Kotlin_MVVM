package com.android.practice.github_repository_search_kotlin_mvvm

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.practice.github_repository_search_kotlin_mvvm.Model.RepoModel
import com.android.practice.github_repository_search_kotlin_mvvm.PersonaActivity.PersonalActivity
import com.android.practice.github_repository_search_kotlin_mvvm.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var txtResults: TextView
    lateinit var mainViewModel: SharedViewModel
    lateinit var recyclerViewAdapter: recyclerViewAdapter
    lateinit var editSearch: EditText
    lateinit var btnPersonal: Button
    lateinit var btnSearch: ImageButton
    var firstTimeLoad = true
    var page = 1
    var isLoading = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewMain)
        txtResults = findViewById(R.id.heading_label)
        editSearch = findViewById(R.id.search_field);
        btnPersonal = findViewById(R.id.btn_pesonal)
        btnSearch = findViewById(R.id.search_btn);
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager


        recyclerViewAdapter = recyclerViewAdapter(this, null)
        firstTimeLoad = true


        mainViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        Log.d("Main", "Load More" + page)
        mainViewModel.runData("Android", page)
        mainViewModel.toMain.observe(this, {
            handleData(it)
        })

        btnSearch.setOnClickListener(View.OnClickListener {
            if (!editSearch.text.isEmpty()) {
                page = 1
                recyclerView.clearOnScrollListeners()
                recyclerViewAdapter.clearData()
                mainViewModel.runData(editSearch.text.toString(), page)
            }
        })
        btnPersonal.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, PersonalActivity::class.java))
        })

    }

    fun handleData(it: RepoModel) {
        if (firstTimeLoad) {
            recyclerViewAdapter = recyclerViewAdapter(this, it)
            recyclerView.adapter = recyclerViewAdapter
            firstTimeLoad = false
            Log.d("Main", "LoadFirst")
        } else {
            Log.d("Main", "LoadSecond")
            recyclerViewAdapter.addData(it)
            isLoading = false
        }
        recyclerView.addOnScrollListener(object : PaginationListener() {
            override fun loadMoreItems() {
                if (!isLoading) {
                    Log.d("Main", "Load More" + page)
                    mainViewModel.runData("Android", page)
                    isLoading = true
                }
            }
        })

        val totalResults: Int = it.totalCount
        val currentResults: Int = recyclerViewAdapter.itemCount
        heading_label.text = "Results: " + totalResults

        if (currentResults == totalResults) {
            recyclerView.clearOnScrollListeners()
        } else {
            recyclerViewAdapter.addLoading()
        }
        page++
    }
}