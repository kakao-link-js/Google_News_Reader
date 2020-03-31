package com.kotlin.jaesungchi.rss_news_reader.Model

import android.util.Log
import com.kotlin.jaesungchi.rss_news_reader.*
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.ModelCallBacks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

/*
 Google RSS에서 온 정보를 저장하고 업데이트 하는 클래스.
 콜백함수를 인자로 받아 결과를 콜백으로 돌려준다.
 */
class DataModel (private var mModelCallBacks: ModelCallBacks) {
    private  var TAG = "DataModel"
    private var newsData : ArrayList<NewsDTO>? = null

    fun clearNewsData(){
        newsData!!.clear()
    }

    fun addNewsData(newData : NewsDTO) {
        Log.d(TAG,"addNewsData Start")
        if(newsData == null)
            newsData = ArrayList()

        newsData!!.add(newData)
        mModelCallBacks.onModelUpdated(newData)
        Log.d(TAG,"addNewsData End")
    }
}