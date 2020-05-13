package com.kotlin.jaesungchi.rss_news_reader.Presenter

import android.util.Log
import com.kotlin.jaesungchi.rss_news_reader.*
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.AdapterContract
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.ModelCallBacks
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.MainContract
import com.kotlin.jaesungchi.rss_news_reader.Model.NewsDTO
import com.kotlin.jaesungchi.rss_news_reader.Model.NewsModel
import com.kotlin.jaesungchi.rss_news_reader.util.SSLConnect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.IOException
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory


class NewsPresenter() : ModelCallBacks,MainContract.Presenter{
    override var adapterModel: AdapterContract.Model? = null
    override var adapterView: AdapterContract.View? = null
    override lateinit var mView : MainContract.View
    private var newsModel = NewsModel(this)


    override fun onRefreshModel() {
        adapterModel?.clear()
        downloadData()
    }

    override fun onModelUpdated() {
        adapterView?.notifyAdapter()
        mView.stopDialog()
    }

    override fun uploadAdapterData(News : NewsDTO) {
        adapterModel?.add(News)
    }

    override fun downloadData(){
        mView.runDialog()
        newsModel.downloadData()
    }
}