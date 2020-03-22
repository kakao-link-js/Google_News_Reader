package com.kotlin.jaesungchi.rss_news_reader.Presenter

import com.kotlin.jaesungchi.rss_news_reader.View.ListFragment
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.ModelCallBacks
import com.kotlin.jaesungchi.rss_news_reader.Model.DataModel
import com.kotlin.jaesungchi.rss_news_reader.Model.NewsDTO

class NewsPresenter(private var listFragment: ListFragment) : ModelCallBacks{
    private val mModel : DataModel = DataModel(this)

    fun onNewNewsData(newData : NewsDTO){
        mModel.addNewsDatas(newData)
    }

    override fun onModelUpdated(newsDatas: ArrayList<NewsDTO>) {
        //ListFragment에 업데이트 변수 선언
        listFragment.updateList(newsDatas)
    }

}