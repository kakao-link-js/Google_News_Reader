package com.kotlin.jaesungchi.rss_news_reader.InterFaces

import com.kotlin.jaesungchi.rss_news_reader.Model.NewsDTO

interface ModelCallBacks{
    fun onModelUpdated(newsDatas : ArrayList<NewsDTO>)

    fun onRefreshModel()
}