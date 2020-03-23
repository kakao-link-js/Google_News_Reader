package com.kotlin.jaesungchi.rss_news_reader.Presenter

import android.util.Log
import com.kotlin.jaesungchi.rss_news_reader.View.ListFragment
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.ModelCallBacks
import com.kotlin.jaesungchi.rss_news_reader.Model.DataModel
import com.kotlin.jaesungchi.rss_news_reader.Model.NewsDTO
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory


class NewsPresenter(private var listFragment: ListFragment) : ModelCallBacks{
    private val mModel : DataModel = DataModel(this)

    fun getNewsData(newNews:NewsDTO){
        CoroutineScope(Dispatchers.Default).launch {
            var tags : ArrayList<String> = ArrayList()
            var con = Jsoup.connect(newNews.link)
            var doc = con.get()
            var ogTags = doc.select("meta[property^=og:]")
            for(i in ogTags){
                var text = i.attr("property")
                if("og:image".equals(text))
                    newNews.imageLink = i.attr("content")
                else if ("og:description".equals(text))
                    newNews.content = i.attr("content")
            }
            CoroutineScope(Dispatchers.Main).launch {//MainThread에 업데이트를 한다.
                mModel.addNewsData(newNews)
            }
        }
    }

    fun downloadData(){
        CoroutineScope(Dispatchers.Default).launch {
            var newsDatas = ArrayList<NewsDTO>()
            var url = URL("https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko")
            var dbf = DocumentBuilderFactory.newInstance()
            var db = dbf.newDocumentBuilder()
            var doc = db.parse(InputSource(url.openStream()))
            doc.documentElement.normalize()
            var itemNodeList = doc.getElementsByTagName("item")

            for (i in 0 until itemNodeList.length) {
                val node = itemNodeList.item(i)
                val element = node as Element
                val title = element.getElementsByTagName("title").item(0).childNodes.item(0).nodeValue
                val link = element.getElementsByTagName("link").item(0).childNodes.item(0).nodeValue
                val pubDate = element.getElementsByTagName("pubDate").item(0).childNodes.item(0).nodeValue
                newsDatas.add(NewsDTO(title,"",link,pubDate,"", ArrayList()))
            }
            for(newsData in newsDatas)
                getNewsData(newsData)
        }
    }

    fun clearData(){
        mModel.clearNewsData()
    }

    override fun onModelUpdated(newsDatas: ArrayList<NewsDTO>) {
        listFragment.updateList(newsDatas)
    }

}