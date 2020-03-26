package com.kotlin.jaesungchi.rss_news_reader.Presenter

import android.util.Log
import com.kotlin.jaesungchi.rss_news_reader.*
import com.kotlin.jaesungchi.rss_news_reader.View.ListFragment
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.ModelCallBacks
import com.kotlin.jaesungchi.rss_news_reader.Model.DataModel
import com.kotlin.jaesungchi.rss_news_reader.Model.NewsDTO
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory


class NewsPresenter(private var listFragment: ListFragment) : ModelCallBacks{

    private val mModel : DataModel = DataModel(this)
    private val TAG = "NewPresenter"

    private fun getNewsData(links : ArrayList<String>){
        Log.d(TAG,"getNewsData Start")
        CoroutineScope(Dispatchers.Default).launch {
            for(link in links) {
                var newNews = NewsDTO("", "", link, "", ArrayList())
                var con = Jsoup.connect(link).ignoreContentType(true).timeout(3000)
                var statusCode = con.ignoreHttpErrors(true).execute().statusCode()
                if (statusCode.toString()[0] != '2') //2로시작하는 응답은 성공
                    continue
                var doc = con.get()
                newNews.title = doc.title()
                var ogTags = doc.select(OG_BASE_WORD)
                for (i in ogTags) {
                    when (i.attr(OG_PROPERTY_WORD)) {
                        OG_IMAGE_WORD -> newNews.imageLink = i.attr(OG_CONTENT_WORD)
                        OG_DESCRIPTION_WORD -> newNews.content = i.attr(OG_CONTENT_WORD)
                    }
                }
                if (newNews.title.isNullOrBlank() || newNews.content.isNullOrBlank())
                    continue
                newNews.tags = calculateKeywordinContent(newNews.content)
                CoroutineScope(Dispatchers.Main).launch {
                    mModel.addNewsData(newNews)  //MainThread에 업데이트를 한다
                }
            }

        }
        Log.d(TAG,"getNewsData End")
    }

    //Content를 통해 Tag를 추출하여 반환하는
    private fun calculateKeywordinContent(content : String) : ArrayList<String>{
        Log.d(TAG,"calculateKeyword Start")
        var regex = """[^\uAC00-\uD7A30-9a-zA-Z\s]""".toRegex()
        var arr = content.replace(regex," ").split(' ').map{ it.trim()}.sorted()
        var countArr = ArrayList<Int>()
        var wordArr = ArrayList<String>()
        var beforeWord = arr[0]
        for(word in arr)
            if(!word.isNullOrBlank()) {
                beforeWord = word
                break
            }
        var countNow = 1
        for(i in 1 until arr.size ){
            if(arr[i].isNullOrBlank() || arr[i].length < 2)
                continue
            if(beforeWord == arr[i]){
                countNow++
                continue
            }
            //countArr 이랑 비교해서 ArrayList 에 들어가야함
            if(wordArr.size == 0){
                wordArr.add(beforeWord)
                countArr.add(countNow)
            }
            else {
                for (i in wordArr.size - 1 downTo 0) {
                    if (countNow <= countArr[i]) {
                        if (wordArr.size < 3) {
                            wordArr.add(beforeWord)
                            countArr.add(countNow)
                        }
                        break
                    }
                    if (i != 2) {
                        if (wordArr.size <= i + 1) {
                            wordArr.add(wordArr[i])
                            countArr.add(countArr[i])
                        } else {
                            wordArr[i + 1] = wordArr[i]
                            countArr[i + 1] = countArr[i]
                        }
                    }
                    wordArr[i] = beforeWord
                    countArr[i] = countNow
                }
            }
            beforeWord = arr[i]
            countNow = 1
        }
        Log.d(TAG,"calculateKeyword End")
        Log.d(TAG,wordArr.toString())
        return wordArr
    }

    fun downloadData(){
        Log.d(TAG,"downloadData Start")
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
            getNewsData(newsLinks)
        }
        Log.d(TAG,"downloadData End")
    }


    fun clearData(){
        mModel.clearNewsData()
    }

    override fun onModelUpdated(newsData: ArrayList<NewsDTO>) {
        listFragment.updateList(newsData)
    }
}