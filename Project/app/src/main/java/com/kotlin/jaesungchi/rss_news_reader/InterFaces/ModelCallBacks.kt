package com.kotlin.jaesungchi.rss_news_reader.InterFaces

import com.kotlin.jaesungchi.rss_news_reader.Model.newsDTO

interface ModelCallBacks{
    fun onModelUpdated(newsDatas : ArrayList<newsDTO>)
}