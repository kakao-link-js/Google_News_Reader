package com.kotlin.jaesungchi.rss_news_reader.InterFaces

/*
    Google의 Contract 정의를 따른다.
 */
interface MainContract{

    interface View{
        fun showMessage(message : String)
        fun stopDialog()
        fun runDialog()
    }

    interface Presenter{
        var mView : View
        var adapterModel : AdapterContract.Model?
        var adapterView : AdapterContract.View?
        fun downloadData()
        fun onRefreshModel()
    }
}