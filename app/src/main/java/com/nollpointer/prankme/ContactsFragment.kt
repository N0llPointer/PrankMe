package com.nollpointer.prankme


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.DividerItemDecoration


class ContactsFragment : Fragment(), ContactsAdapter.Listener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val recyclerView: RecyclerView =  inflater.inflate(R.layout.fragment_contacts, container, false) as RecyclerView

        val layoutManager = LinearLayoutManager(activity)

        recyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
                layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val list = listOf(Contact("Mike",1),Contact("Nike",2),Contact("Bike",3),Contact("Like",4),Contact("Hike",5))
        val adapter = ContactsAdapter(list,listener = this)

        recyclerView.adapter = adapter

        return recyclerView
    }

    override fun onClick(position: Int) {
        activity!!.supportFragmentManager!!.
                beginTransaction().
                //setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).
                        replace(R.id.frame_layout,ChatFragment()).
                        addToBackStack(null).commit()
    }
}
