package com.kotlin.jaesungchi.rss_news_reader.Model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.ModelCallBacks
import com.kotlin.jaesungchi.rss_news_reader.R

class ListRvAdapter(val context: Context,val callbacks :ModelCallBacks ,val itemClick : (NewsDTO) -> Unit) : RecyclerView.Adapter<ListRvAdapter.Holder>(){

    val itemList: ArrayList<NewsDTO> = ArrayList()

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_rv_item,parent,false)
        return Holder(view)
    }

    fun add(data : NewsDTO){
        itemList.add(data)
        callbacks.onModelUpdated()
    }

    fun clear(){
        itemList.clear()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val newsimage = itemView.findViewById<ImageView>(R.id.itemImage)
        val newsTitle = itemView.findViewById<TextView>(R.id.itemTitle)
        val newsContent  = itemView.findViewById<TextView>(R.id.itemContent)
        val newsTag1 = itemView.findViewById<TextView>(R.id.itemTag1)
        val newsTag2 = itemView.findViewById<TextView>(R.id.itemTag2)
        val newsTag3 = itemView.findViewById<TextView>(R.id.itemTag3)

        fun bind(news : NewsDTO){
            Glide.with(context).load(news.imageLink)
                .placeholder(R.drawable.loader)
                .error(R.drawable.error)
                .into(newsimage)
            newsTitle.text = news.title.trim()
            newsContent.text = news.content.trim()
            if(news.tags.size > 0) {
                newsTag1.text = news.tags[0]
                newsTag1.visibility = View.VISIBLE
            }
            if(news.tags.size > 1) {
                newsTag2.text = news.tags[1]
                newsTag2.visibility = View.VISIBLE
            }
            if(news.tags.size > 2) {
                newsTag3.text = news.tags[2]
                newsTag3.visibility = View.VISIBLE
            }
            itemView.setOnClickListener { itemClick(news) }
        }
    }
}