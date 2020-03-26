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
        newsData = null
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
            getNewsData(newsLinks)
        }
    }

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
                newNews.tags = getKeywordinContent(newNews.content)
                CoroutineScope(Dispatchers.Main).launch {
                    addNewsData(newNews)  //MainThread에 업데이트를 한다
                }
            }

        }
        Log.d(TAG,"getNewsData End")
    }

    //Content를 통해 Tag를 추출하여 반환하는
    private fun getKeywordinContent(content : String) : ArrayList<String>{
        var regex = """[^\uAC00-\uD7A30-9a-zA-Z\s]""".toRegex()
        var arr = content.replace(regex," ").split(' ').map{ it.trim()}.sorted()
        var countArr = ArrayList<Int>()
        var wordArr = ArrayList<String>()
        var beforeWord = arr[0]
        var arrIndex = 0
        var countNow = 1
        for(index in 0 until arr.size) //오름차순 정렬 후 빈칸과 작은 글자는 넘어간다.
            if(!arr[index].isNullOrBlank() && arr[index].length > 1) {
                beforeWord = arr[index]
                arrIndex = index + 1// 다음글자부터 순환
                break
            }
        for(i in arrIndex until arr.size ){
            if(arr[i].length < 2) //글자가 작은경우 넘어간다.
                continue
            if(beforeWord == arr[i]){ //글자가 같은경우 카운트를 늘리고 다음글자.
                countNow++
                continue
            }
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
        return wordArr
    }

    fun addNewsData(newData : NewsDTO) {
        Log.d(TAG,"addNewsData Start")
        if(newsData == null)
            newsData = ArrayList()

        newsData!!.add(newData)
        mModelCallBacks.onModelUpdated(newsData!!)
        Log.d(TAG,"addNewsData End")
    }
}