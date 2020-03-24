package com.kotlin.jaesungchi.rss_news_reader.Presenter

import android.util.Log
import com.bumptech.glide.load.HttpException
import com.kotlin.jaesungchi.rss_news_reader.*
import com.kotlin.jaesungchi.rss_news_reader.View.ListFragment
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.ModelCallBacks
import com.kotlin.jaesungchi.rss_news_reader.Model.DataModel
import com.kotlin.jaesungchi.rss_news_reader.Model.NewsDTO
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.net.HttpCookie
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory


class NewsPresenter(private var listFragment: ListFragment) : ModelCallBacks{
    private val mModel : DataModel = DataModel(this)

    private fun getNewsData(link : String){
        CoroutineScope(Dispatchers.Default).launch {
            var newNews = NewsDTO("","",link,"",ArrayList())
            var con = Jsoup.connect(link)
            var statusCode = con.ignoreHttpErrors(true).execute().statusCode()
            if(statusCode != 200)
                return@launch
            var doc = con.get()
            var ogTags = doc.select(OG_BASE_WORD)
            for(i in ogTags){
                var text = i.attr(OG_PROPERTY_WORD)
                if(OG_IMAGE_WORD == text)
                    newNews.imageLink = i.attr(OG_CONTENT_WORD)
                else if (OG_DESCRIPTION_WORD == text)
                    newNews.content = i.attr(OG_CONTENT_WORD)
                else if (OG_TITLE_WORD == text)
                    newNews.title = i.attr(OG_CONTENT_WORD)
            }
            newNews.tags = calculateKeywordinContent(newNews.content)
            //내용이나 제목이 제대로 Parsing 되지 않은 경우는 걸러낸다.
            if(!newNews.title.isNullOrEmpty() && !newNews.content.isNullOrEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    mModel.addNewsData(newNews)  //MainThread에 업데이트를 한다.
                }
            }
        }
    }

    private fun calculateKeywordinContent(content : String) : ArrayList<String>{
        val arr = content.split(' ').map{ it.toString() }.sorted()
        Log.e("test",arr.joinToString(" "))
        return ArrayList()
    }

    fun downloadData(){
        CoroutineScope(Dispatchers.Default).launch {
            var newsLinks = ArrayList<String>()
            var url = URL(GOOGLE_RSS_URL)
            var dbf = DocumentBuilderFactory.newInstance()
            var db = dbf.newDocumentBuilder()
            var doc = db.parse(InputSource(url.openStream()))
            doc.documentElement.normalize()
            var itemNodeList = doc.getElementsByTagName(ITEM_WORD)

            for (index in 0 until itemNodeList.length) {
                val node = itemNodeList.item(index)
                val element = node as Element
                val link = element.getElementsByTagName(LINK_WORD).item(0).childNodes.item(0).nodeValue
                newsLinks.add(link)
            }
            for(link in newsLinks)
                getNewsData(link)
        }
    }

    fun clearData(){
        mModel.clearNewsData()
    }

    override fun onModelUpdated(newsDatas: ArrayList<NewsDTO>) {
        listFragment.updateList(newsDatas)
    }

}