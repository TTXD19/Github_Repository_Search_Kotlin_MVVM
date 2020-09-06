package com.android.practice.github_repository_search_kotlin_mvvm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.practice.github_repository_search_kotlin_mvvm.Model.Item
import com.android.practice.github_repository_search_kotlin_mvvm.Model.RepoModel
import com.squareup.picasso.Picasso

class recyclerViewAdapter(context: Context, dataLsit: RepoModel): RecyclerView.Adapter<recyclerViewAdapter.viewHolder>() {

    var mContext: Context
    var mDataList: RepoModel

    init {
        mContext = context
        mDataList = dataLsit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
       val layout = LayoutInflater.from(mContext).inflate(R.layout.list_item_layout, parent, false)
        return viewHolder(layout)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val itemData = mDataList.items.get(position)
        val ownerData = mDataList.items.get(position).owner

        holder.txtUserName.text = ownerData.login
        Picasso.get().load(ownerData.avatarUrl).into(holder.userImage)
        holder.repoName.text = itemData.fullName
        holder.description.text = itemData.description
        holder.starCount.text = itemData.stargazersCount.toString()
    }

    override fun getItemCount(): Int {
        return mDataList.items.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtUserName: TextView = itemView.findViewById(R.id.user_name)
        val userImage: ImageView = itemView.findViewById(R.id.img_userImage)
        val repoName: TextView = itemView.findViewById(R.id.repository_name)
        val description: TextView = itemView.findViewById(R.id.description)
        val starCount: TextView = itemView.findViewById(R.id.star_count)
    }


}