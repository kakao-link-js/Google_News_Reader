package com.kotlin.jaesungchi.rss_news_reader.Model

import android.util.Log
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.ModelCallBacks

/*
 Google RSS에서 온 정보를 저장하고 업데이트 하는 클래스.
 콜백함수를 인자로 받아 결과를 콜백으로 돌려준다.
 */
class DataModel (private var mModelCallBacks: ModelCallBacks) {
    private var newsDatas : ArrayList<NewsDTO>? = null

    fun clearNewsData(){
        newsDatas = null
    }

    fun addNewsData(newData : NewsDTO) {
        if(newsDatas == null)
            newsDatas = ArrayList()

        newsDatas!!.add(newData)
        //콜백함수를 통해 데이터가 업데이트 된것을 알려준다.
        mModelCallBacks.onModelUpdated(newsDatas!!)
    }
}