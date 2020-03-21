package com.kotlin.jaesungchi.rss_news_reader.Model


//Google RSS를 통해 온 데이터를 저장하기 위한 규칙

data class newsDTO(
    val title : String,
    val link : String,
    val pubDate : String,
    val imageLink : String? = null,
    val tags : ArrayList<String> =  ArrayList())