package com.kotlin.jaesungchi.rss_news_reader.View

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kotlin.jaesungchi.rss_news_reader.Model.NewsDTO
import com.kotlin.jaesungchi.rss_news_reader.Presenter.NewsPresenter
import com.kotlin.jaesungchi.rss_news_reader.R

class ListFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener{

    private var mNewsPresenter = NewsPresenter(this)
    private var mRecyclerView : RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_list,container,false)
        view.findViewById<SwipeRefreshLayout>(R.id.swipe_layout).setOnRefreshListener(this)

        mNewsPresenter = NewsPresenter(this)
        mRecyclerView = view.findViewById(R.id.recycler_view)
        mRecyclerView!!.layoutManager = LinearLayoutManager(view.context)
        mRecyclerView!!.setHasFixedSize(true)
        mNewsPresenter.downloadData()
        return view
    }

    fun updateList(list:ArrayList<NewsDTO>){
        val listAdapter = context?.let {
            ListRvAdapter(it, list)
        }
        mRecyclerView!!.adapter = listAdapter
        mRecyclerView!!.scrollToPosition(0)
    }

    override fun onRefresh() {
        mNewsPresenter.clearData()
        mNewsPresenter.downloadData()
        view!!.findViewById<SwipeRefreshLayout>(R.id.swipe_layout).isRefreshing = false
    }
}