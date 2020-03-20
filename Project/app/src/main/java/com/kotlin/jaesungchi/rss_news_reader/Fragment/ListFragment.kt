package com.kotlin.jaesungchi.rss_news_reader.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kotlin.jaesungchi.rss_news_reader.R

class ListFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_list,container,false)
        view.findViewById<SwipeRefreshLayout>(R.id.swipe_layout).setOnRefreshListener(this)
        return view
    }

    override fun onRefresh() {
        //새로 고침시 발생하는 코드

        view!!.findViewById<SwipeRefreshLayout>(R.id.swipe_layout).isRefreshing = false
    }

}