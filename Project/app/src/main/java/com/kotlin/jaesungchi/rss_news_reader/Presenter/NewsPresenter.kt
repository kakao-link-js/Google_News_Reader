package com.kotlin.jaesungchi.rss_news_reader.Presenter

import com.kotlin.jaesungchi.rss_news_reader.InterFaces.AdapterContract
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.ModelCallBacks
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.MainContract
import com.kotlin.jaesungchi.rss_news_reader.Model.NewsDTO
import com.kotlin.jaesungchi.rss_news_reader.Model.NewsModel


class NewsPresenter() : ModelCallBacks,MainContract.Presenter{
    override var adapterModel: AdapterContract.Model? = null
    override var adapterView: AdapterContract.View? = null
    override lateinit var mView : MainContract.View
    private var newsModel = NewsModel(this)


    override fun onRefreshModel() {
        adapterModel?.clear()
        downloadData()
    }

    override fun onModelUpdated(News : NewsDTO) {
        adapterModel?.add(News)
    }

    override fun downloadData(){
        mView.runDialog()
        newsModel.downloadData()
    }
}