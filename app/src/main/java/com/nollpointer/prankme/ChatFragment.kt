package com.nollpointer.prankme


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

class ChatFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val act = activity as AppCompatActivity
        act.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val mainView = inflater.inflate(R.layout.fragment_chat, container, false)
        val listView: ListView = mainView.findViewById(R.id.chat_list_view)
        listView.adapter = ChatAdapter(act, arrayOf("1","1","1","1","1","1","1","1","1","1","1","1","1","1"))
        return mainView
    }


}
