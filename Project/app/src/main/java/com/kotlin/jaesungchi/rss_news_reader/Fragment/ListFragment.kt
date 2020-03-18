package com.kotlin.jaesungchi.rss_news_reader.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.kotlin.jaesungchi.rss_news_reader.R

class ListFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_list,container,false)
        view.findViewById<Button>(R.id.button).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_list_screen_to_web_screen)
        }
        return view
    }
}