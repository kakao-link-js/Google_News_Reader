package com.kotlin.jaesungchi.rss_news_reader.Presenter

import com.kotlin.jaesungchi.rss_news_reader.View.ListFragment
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.ModelCallBacks
import com.kotlin.jaesungchi.rss_news_reader.Model.DataModel
import com.kotlin.jaesungchi.rss_news_reader.Model.NewsDTO


class NewsPresenter(private var listFragment: ListFragment) : ModelCallBacks{

    private val mModel : DataModel = DataModel(this)

    fun downloadData(){
        mModel.downloadData()
    }

    fun clearData(){
        mModel.clearNewsData()
        mModel.downloadData()
    }

    override fun onModelUpdated(newsData: ArrayList<NewsDTO>) {
        listFragment.updateList(newsData)
    }
}