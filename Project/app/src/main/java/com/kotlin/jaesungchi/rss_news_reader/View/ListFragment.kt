package com.kotlin.jaesungchi.rss_news_reader.View

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kotlin.jaesungchi.rss_news_reader.InterFaces.MainContract
import com.kotlin.jaesungchi.rss_news_reader.LINK_WORD
import com.kotlin.jaesungchi.rss_news_reader.Model.ListRvAdapter
import com.kotlin.jaesungchi.rss_news_reader.Presenter.NewsPresenter
import com.kotlin.jaesungchi.rss_news_reader.R

class ListFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener,MainContract.View{
    private lateinit var presenter : MainContract.Presenter
    private var adapter : ListRvAdapter? = null
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var asyncDialog : ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_list,container,false)
        view.findViewById<SwipeRefreshLayout>(R.id.swipe_layout).setOnRefreshListener(this)
        mRecyclerView = view.findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(view.context)
        mRecyclerView.setHasFixedSize(true)
        initProgressDiaglog()
        //adaper 설정
        if(adapter == null) {
            adapter = context?.let {
                ListRvAdapter(it) { news ->
                    var bundle = Bundle()
                    bundle.putString(LINK_WORD, news.link)
                    view?.let { it ->
                        Navigation.findNavController(it)
                            .navigate(R.id.action_list_screen_to_web_screen, bundle)
                    }
                }
            }
        }
        mRecyclerView.adapter = adapter
        presenter = NewsPresenter().apply{
            view = this@ListFragment
            adapterView = adapter
            adapterModel = adapter
        }
        presenter.downloadData()

        return view
    }

    private fun initProgressDiaglog(){
        asyncDialog = ProgressDialog(context)
        asyncDialog.setCancelable(false)
        asyncDialog.setMessage("뉴스를 다운받고 있습니다...")
    }

    override fun onRefresh() {
        presenter.onRefreshModel()
        view?.findViewById<SwipeRefreshLayout>(R.id.swipe_layout)?.isRefreshing = false
    }

    override fun showMessage(message: String) {
        Toast.makeText(this.context,"인터넷 연결상태를 확인 해 주세요.",Toast.LENGTH_LONG).show()
    }

    override fun stopDialog() {
    }

    override fun runDialog() {
        asyncDialog.dismiss()
    }
}