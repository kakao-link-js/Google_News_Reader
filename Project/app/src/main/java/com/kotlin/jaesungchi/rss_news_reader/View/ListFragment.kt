package com.kotlin.jaesungchi.rss_news_reader.View

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kotlin.jaesungchi.rss_news_reader.Presenter.NewsPresenter
import com.kotlin.jaesungchi.rss_news_reader.R

class ListFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener{
    private var TAG = "ListFragment"
    private var mNewsPresenter = NewsPresenter(this)
    var mRecyclerView : RecyclerView? = null
    var asyncDialog : ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG,"onCreateView Start")
        var view = inflater.inflate(R.layout.fragment_list,container,false)
        view.findViewById<SwipeRefreshLayout>(R.id.swipe_layout).setOnRefreshListener(this)
        mRecyclerView = view.findViewById(R.id.recycler_view)
        mRecyclerView?.layoutManager = LinearLayoutManager(view.context)
        mRecyclerView?.setHasFixedSize(true)
        initProgressDiaglog()
        mNewsPresenter = NewsPresenter(this)
        mNewsPresenter.connectAdapter()
        mNewsPresenter.downloadData()
        Log.d(TAG,"onCreateView End")
        return view
    }

    private fun initProgressDiaglog(){
        asyncDialog = ProgressDialog(context)
        asyncDialog?.setCancelable(false)
        asyncDialog?.setMessage("뉴스를 다운받고 있습니다...")
    }

    override fun onRefresh() {
        mNewsPresenter.onRefreshModel()
        view?.findViewById<SwipeRefreshLayout>(R.id.swipe_layout)?.isRefreshing = false
    }
}