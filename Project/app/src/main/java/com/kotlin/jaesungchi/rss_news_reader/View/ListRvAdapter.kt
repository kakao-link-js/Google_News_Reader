package com.kotlin.jaesungchi.rss_news_reader.View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.jaesungchi.rss_news_reader.Model.NewsDTO
import com.kotlin.jaesungchi.rss_news_reader.R

class ListRvAdapter(val context: Context, val itemList: ArrayList<NewsDTO>) : RecyclerView.Adapter<ListRvAdapter.Holder>(){

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(itemList[position],context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_rv_item,parent,false)
        return Holder(view)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val newsimage = itemView.findViewById<ImageView>(R.id.itemImage)
        val newsTitle = itemView.findViewById<TextView>(R.id.itemTitle)
        val newsContent  = itemView.findViewById<TextView>(R.id.itemContent)
        val newsTag1 = itemView.findViewById<TextView>(R.id.itemTag1)
        val newsTag2 = itemView.findViewById<TextView>(R.id.itemTag2)
        val newsTag3 = itemView.findViewById<TextView>(R.id.itemTag3)

        fun bind(news : NewsDTO, context : Context){
            newsimage.setImageResource(R.drawable.rssicon)
            newsTitle.text = news.title
            newsContent.text = news.content
            if(news.tags.size > 0)
                newsTag1.text = news.tags[0]
            if(news.tags.size > 1)
                newsTag2.text = news.tags[1]
            if(news.tags.size > 2)
                newsTag3.text = news.tags[2]
        }
    }
}