package com.kotlin.jaesungchi.rss_news_reader.InterFaces

import com.kotlin.jaesungchi.rss_news_reader.Model.NewsDTO

interface AdapterContract{
    interface View{

        fun notifyAdapter()
    }
    interface Model{
        fun add(data : NewsDTO)

        fun clear()

        fun getItem(position : Int) : NewsDTO

    }
}