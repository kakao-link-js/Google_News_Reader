package com.kotlin.jaesungchi.rss_news_reader.View

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kotlin.jaesungchi.rss_news_reader.LINK_WORD
import com.kotlin.jaesungchi.rss_news_reader.Model.NewsDTO
import com.kotlin.jaesungchi.rss_news_reader.Presenter.NewsPresenter
import com.kotlin.jaesungchi.rss_news_reader.R

class ListFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener{
    private var TAG = "ListFragment"
    private var mNewsPresenter = NewsPresenter(this)
    private var mRecyclerView : RecyclerView? = null
    private var mListAdapter : ListRvAdapter? = null
    private var asyncDialog : ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG,"onCreateView Start")
        var view = inflater.inflate(R.layout.fragment_list,container,false)
        view.findViewById<SwipeRefreshLayout>(R.id.swipe_layout).setOnRefreshListener(this)

        mRecyclerView = view.findViewById(R.id.recycler_view)
        mRecyclerView!!.layoutManager = LinearLayoutManager(view.context)
        mRecyclerView!!.setHasFixedSize(true)
        initProgressDiaglog()
        if(mListAdapter == null) { //프래그먼트 첫 생성시
            setListAdapter(ArrayList())
            asyncDialog!!.show()
        }
        mRecyclerView!!.adapter = mListAdapter
        mNewsPresenter = NewsPresenter(this)
        mNewsPresenter.downloadData()
        Log.d(TAG,"onCreateView End")
        return view
    }

    private fun initProgressDiaglog(){
        asyncDialog = ProgressDialog(context)
        asyncDialog!!.setCancelable(false)
        asyncDialog!!.setMessage("뉴스를 다운받고 있습니다...")
    }

    fun updateList(list:ArrayList<NewsDTO>){
        setListAdapter(list)
        mRecyclerView!!.adapter = mListAdapter
        asyncDialog!!.dismiss()
    }

    fun setListAdapter(list : ArrayList<NewsDTO>){
        mListAdapter= context?.let {
            ListRvAdapter(it, list) { news ->
                var bundle = Bundle()
                bundle.putString(LINK_WORD, news.link)
                view?.let { it ->
                    Navigation.findNavController(it).navigate(R.id.action_list_screen_to_web_screen, bundle)
                }
            }
        }
    }

    override fun onRefresh() {
        mNewsPresenter.clearData()
        asyncDialog!!.show()
        mNewsPresenter.downloadData()
        view!!.findViewById<SwipeRefreshLayout>(R.id.swipe_layout).isRefreshing = false
    }
}