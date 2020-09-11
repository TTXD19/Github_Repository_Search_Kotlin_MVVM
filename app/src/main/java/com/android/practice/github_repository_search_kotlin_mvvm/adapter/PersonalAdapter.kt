package com.android.practice.github_repository_search_kotlin_mvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.practice.github_repository_search_kotlin_mvvm.Model.Item
import com.android.practice.github_repository_search_kotlin_mvvm.Model.RepoModel
import com.android.practice.github_repository_search_kotlin_mvvm.R
import com.squareup.picasso.Picasso

class PersonalAdapter(context: Context, itemList: MutableList<Item?>): RecyclerView.Adapter<PersonalAdapter.PersonalViewHolder>(), MainAdapterOnClickListener {

    var mContext: Context
    var mItemList: MutableList<Item?>
    lateinit var mainOnClick: MainOnClickListener

    init {
        mContext = context
        mItemList = itemList
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.list_item_layout, parent, false)
        return PersonalViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: PersonalViewHolder, position: Int) {
        val itemData = mItemList.get(position)
        val ownerData = itemData!!.owner
        holder.txtUserName.text = ownerData.login
        Picasso.get().load(ownerData.avatarUrl).into(holder.userImage)
        holder.repoName.text = itemData.fullName
        holder.description.text = itemData.description
        holder.starCount.text = itemData.stargazersCount.toString()
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    fun onClickListener(mainOnClick: MainOnClickListener){
        this.mainOnClick = mainOnClick
    }

    class PersonalViewHolder(itemView: View, listener:MainAdapterOnClickListener) : RecyclerView.ViewHolder(itemView){

        val txtUserName: TextView = itemView.findViewById(R.id.user_name)
        val userImage: ImageView = itemView.findViewById(R.id.img_userImage)
        val repoName: TextView = itemView.findViewById(R.id.repository_name)
        val description: TextView = itemView.findViewById(R.id.description)
        val starCount: TextView = itemView.findViewById(R.id.star_count)

        init {
            itemView.setOnClickListener(View.OnClickListener {
                val position = adapterPosition
                if (position !=  RecyclerView.NO_POSITION){
                    listener.onClick(position)
                }
            })
        }

    }

    override fun onClick(position: Int) {
        mainOnClick.onClicked(position, mItemList)
    }
}