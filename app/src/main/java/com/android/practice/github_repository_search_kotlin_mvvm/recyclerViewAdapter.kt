package com.android.practice.github_repository_search_kotlin_mvvm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.practice.github_repository_search_kotlin_mvvm.Model.RepoModel
import com.squareup.picasso.Picasso

class recyclerViewAdapter(context: Context, dataLsit: RepoModel?) :
    RecyclerView.Adapter<recyclerViewAdapter.customeViewHolder>() {

    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_NORMAL = 1

    var mContext: Context
    var mDataList: RepoModel?

    init {
        mContext = context
        mDataList = dataLsit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): customeViewHolder {

        var view: View

        if (viewType == VIEW_TYPE_NORMAL) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_layout, parent, false)
            return viewHolder(view)
        }else{
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_loading, parent, false)
            return ProgressHolder(view)
        }
    }

    override fun onBindViewHolder(holder: customeViewHolder, position: Int) {
        if (holder is viewHolder){
            val itemData = mDataList?.items?.get(position)
            val ownerData = mDataList?.items?.get(position)?.owner
            holder.txtUserName.text = ownerData?.login
            Picasso.get().load(ownerData?.avatarUrl).into(holder.userImage)
            holder.repoName.text = itemData?.fullName
            holder.description.text = itemData?.description
            holder.starCount.text = itemData?.stargazersCount.toString()
        }
    }

    override fun getItemCount(): Int {
        if (mDataList?.items?.size != null) {
            return mDataList?.items!!.size
        } else {
            return 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (mDataList?.items?.get(position) != null) {
            return VIEW_TYPE_NORMAL
        } else {
            return VIEW_TYPE_LOADING
        }
    }

    fun addData(newData: RepoModel) {
        removeLoading()
        mDataList!!.items.addAll(newData.items)
        notifyDataSetChanged()
    }

    fun clearData() {
        mDataList!!.items.clear()
        notifyDataSetChanged()
    }

    fun addLoading() {
        mDataList!!.items.add(null)
        notifyDataSetChanged()
    }

    fun removeLoading() {
        mDataList?.items?.remove(null)
    }

    open class customeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }


    class viewHolder(itemView: View) : customeViewHolder(itemView) {

        val txtUserName: TextView = itemView.findViewById(R.id.user_name)
        val userImage: ImageView = itemView.findViewById(R.id.img_userImage)
        val repoName: TextView = itemView.findViewById(R.id.repository_name)
        val description: TextView = itemView.findViewById(R.id.description)
        val starCount: TextView = itemView.findViewById(R.id.star_count)
    }

    class ProgressHolder(itemView: View) : customeViewHolder(itemView) {

    }


}