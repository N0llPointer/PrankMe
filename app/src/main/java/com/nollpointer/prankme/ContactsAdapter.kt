package com.nollpointer.prankme

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ContactsAdapter(val contacts: List<Contact>, val listener: Listener = EmptyListener): RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    interface Listener {
        fun onClick(position: Int)
    }

    class ViewHolder(cardView: CardView) : RecyclerView.ViewHolder(cardView){
        val customCardView: CardView

        init {
            customCardView = cardView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        val c: CardView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_contact, parent, false) as CardView
        return ViewHolder(c)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.customCardView
        var textView: TextView = cardView.findViewById(R.id.contact_name)
        textView.text = contacts.get(position).name

        cardView.setOnClickListener{
            listener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

}

object EmptyListener: ContactsAdapter.Listener{
    override fun onClick(position: Int) {
        Log.wtf("RECYCKERVIEW_CARD","Click performed")
    }
}