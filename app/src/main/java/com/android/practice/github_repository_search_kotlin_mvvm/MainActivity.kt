package com.android.practice.github_repository_search_kotlin_mvvm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.practice.github_repository_search_kotlin_mvvm.Model.Item
import com.android.practice.github_repository_search_kotlin_mvvm.Model.RepoModel
import com.android.practice.github_repository_search_kotlin_mvvm.PersonaActivity.PersonalActivity
import com.android.practice.github_repository_search_kotlin_mvvm.adapter.MainOnClickListener
import com.android.practice.github_repository_search_kotlin_mvvm.adapter.PaginationListener
import com.android.practice.github_repository_search_kotlin_mvvm.adapter.RecyclerViewAdapter
import com.android.practice.github_repository_search_kotlin_mvvm.databinding.ActivityMainBinding
import com.android.practice.github_repository_search_kotlin_mvvm.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var txtResults: TextView
    lateinit var mainViewModel: SharedViewModel
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var editSearch: EditText
    lateinit var btnPersonal: Button
    lateinit var btnSearch: ImageButton
    var firstTimeLoad = true
    var page = 1
    var isLoading = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
       // ActivityMainBinding.bind()
        
        setContentView(R.layout.activity_main)
        recyclerView = recyclerViewMain
        recyclerView = findViewById(R.id.recyclerViewMain)
        txtResults = findViewById(R.id.heading_label)
        editSearch = findViewById(R.id.search_field)
        btnPersonal = findViewById(R.id.btn_pesonal)
        btnSearch = findViewById(R.id.search_btn)
        //val layoutManager = LinearLayoutManager(this)
        //recyclerView.layoutManager = layoutManager

        txtResults = binding.txtPage.apply {
            text = ""
        }
        txtResults = binding.txtPage.also { tt ->
            tt.text = ""
            tt.text = ""
        }
        var abc: String? = null

        val le = abc?.length
        abc?.run {
            length
            val a = toString()
            ""
        }
        val a = txtResults.run {
            ""
        }
        txtResults.let {
            func("11",28)
            func(num = 200)
            func(num = 200,str = "ww")
        }

        recyclerViewAdapter = RecyclerViewAdapter(this, null)
        firstTimeLoad = true


        mainViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        mainViewModel.runData("Android", page)
        mainViewModel.toMain.observe(this, {
            handleData(it)
            recyclerViewAdapter.setOnMainClickListener(object : MainOnClickListener {
                override fun onClicked(position: Int, itemList: MutableList<Item?>) {
                    Toast.makeText(
                        applicationContext,
                        itemList.get(position)!!.fullName,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
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
            recyclerViewAdapter = RecyclerViewAdapter(this, it)
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

    fun func(str: String? = "", num: Int? = null) {

    }
}