package com.kotlin.jaesungchi.rss_news_reader.Presenter

import android.util.Log
import com.kotlin.jaesungchi.rss_news_reader.View.ListFragment
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.ModelCallBacks
import com.kotlin.jaesungchi.rss_news_reader.Model.DataModel
import com.kotlin.jaesungchi.rss_news_reader.Model.NewsDTO
import kotlinx.coroutines.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory


class NewsPresenter(private var listFragment: ListFragment) : ModelCallBacks{
    private val mModel : DataModel = DataModel(this)

    fun downloadData(){
        CoroutineScope(Dispatchers.Default).async {
            val url = URL("https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko")
            val doc : Document
            val dbf = DocumentBuilderFactory.newInstance()
            val db = dbf.newDocumentBuilder()
            doc = db.parse(InputSource(url.openStream()))
            doc.documentElement.normalize()
            val itemNodeList = doc.getElementsByTagName("item")

            for (i in 0 until itemNodeList.length) {
                val node = itemNodeList.item(i)
                val element = node as Element
                val title = element.getElementsByTagName("title").item(0).childNodes.item(0).nodeValue
                val links = element.getElementsByTagName("link").item(0).childNodes.item(0).nodeValue
                val pubDate = element.getElementsByTagName("pubDate").item(0).childNodes.item(0).nodeValue
                CoroutineScope(Dispatchers.Main).launch {//MainThread에 업데이트를 한다.
                    mModel.addNewsData(NewsDTO(title,links,links,pubDate,null,ArrayList()))
                }
            }
        }
    }

    fun clearData(){
        mModel.clearNewsData()
    }

    override fun onModelUpdated(newsDatas: ArrayList<NewsDTO>) {
        listFragment.updateList(newsDatas)
    }

}