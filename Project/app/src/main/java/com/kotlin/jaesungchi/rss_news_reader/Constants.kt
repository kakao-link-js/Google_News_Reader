package com.kotlin.jaesungchi.rss_news_reader

const val SPLASH_TIME_OUT : Long = 1300

const val OG_BASE_WORD = "meta[property^=og:]"
const val OG_PROPERTY_WORD = "property"
const val OG_CONTENT_WORD = "content"
const val OG_DESCRIPTION_WORD = "og:description"
const val OG_IMAGE_WORD = "og:image"
const val OG_TITLE_WORD = "og:title"

const val ITEM_WORD = "item"
const val LINK_WORD = "link"
const val GOOGLE_RSS_URL = "https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko"
