package com.nollpointer.prankme.view

import android.content.Context
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.nollpointer.prankme.Contact
import com.nollpointer.prankme.R

class ContactsView(context: Context, var contacts: List<Contact>,val listener: ContactsAdapter.Listener): DrawerLayout(context) {
    private val recyclerView: RecyclerView
    private val toolbar: Toolbar
    private val drawerLayout: DrawerLayout
    private val adapter: ContactsAdapter

    init {
        val inflater = LayoutInflater.from(context) as LayoutInflater
        inflater.inflate(R.layout.contacts_view,this)
        toolbar = findViewById(R.id.contacts_toolbar)
        recyclerView = findViewById(R.id.contacts_recycler_view)
        drawerLayout = findViewById(R.id.contacts_drawer_layout)

        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
                linearLayoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        adapter = ContactsAdapter(contacts,listener)
        recyclerView.adapter = adapter

        toolbar.setNavigationOnClickListener{
            drawerLayout.openDrawer(Gravity.LEFT)
        }
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        Log.wtf("TAG","$visibility")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.wtf("TAG","ATTACHED")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.wtf("TAG","DETACHED")
    }

}



