package com.kotlin.jaesungchi.rss_news_reader.Model


//Google RSS를 통해 온 데이터를 저장하기 위한 규칙

data class NewsDTO(
    var title : String,
    var content : String,
    var link : String,
    var imageLink : String,
    var tags : ArrayList<String> =  ArrayList()
)