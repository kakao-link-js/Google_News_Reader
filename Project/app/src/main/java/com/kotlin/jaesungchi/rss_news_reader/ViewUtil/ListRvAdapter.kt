package com.kotlin.jaesungchi.rss_news_reader.ViewUtil

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.jaesungchi.rss_news_reader.Model.NewsDTO
import com.kotlin.jaesungchi.rss_news_reader.R

class ListRvAdapter(val context: Context, val itemList: ArrayList<NewsDTO>) : RecyclerView.Adapter<ListRvHolder>(){
    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ListRvHolder, position: Int) {
        holder?.bind(itemList[position],context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRvHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_rv_item,parent,false)
        return ListRvHolder(view)
    }


}