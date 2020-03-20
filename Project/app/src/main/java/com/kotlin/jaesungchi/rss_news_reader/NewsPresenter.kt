package com.kotlin.jaesungchi.rss_news_reader

import com.kotlin.jaesungchi.rss_news_reader.Fragment.ListFragment
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.ModelCallBacks
import com.kotlin.jaesungchi.rss_news_reader.Model.DataModel
import com.kotlin.jaesungchi.rss_news_reader.Model.newsDTO

class NewsPresenter(private var listFragment: ListFragment) : ModelCallBacks{
    private val mModel : DataModel
    init{
        this.mModel = DataModel(this)
    }


    override fun onModelUpdated(newsDatas: ArrayList<newsDTO>) {
        //ListFragment에 업데이트 변수 선언
        if(newsDatas.size > 0){
        }
    }

}