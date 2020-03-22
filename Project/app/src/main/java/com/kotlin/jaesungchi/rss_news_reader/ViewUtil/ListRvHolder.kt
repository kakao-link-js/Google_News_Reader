package com.kotlin.jaesungchi.rss_news_reader.ViewUtil

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.jaesungchi.rss_news_reader.Model.NewsDTO
import com.kotlin.jaesungchi.rss_news_reader.R

class ListRvHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val newsimage = itemView.findViewById<ImageView>(R.id.itemImage)
    val newsTitle = itemView.findViewById<TextView>(R.id.itemTitle)
    val newsTag1 = itemView.findViewById<TextView>(R.id.itemTag1)
    val newsTag2 = itemView.findViewById<TextView>(R.id.itemTag2)
    val newsTag3 = itemView.findViewById<TextView>(R.id.itemTag3)

    fun bind(news : NewsDTO, context : Context){
        newsimage.setImageResource(R.drawable.rssicon)
        newsTitle.text = news.title
        newsTag1.text = "tag1 입니다."
        newsTag2.text = "tag2 입니다."
        newsTag3.text = "tag3 입니다."
    }
}