package com.nollpointer.prankme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class ChatAdapter(val currentContext: Context,val messages: Array<String>): ArrayAdapter<String>(currentContext,-1,messages) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.message_layout, parent, false)
        val textView: TextView = rowView.findViewById(R.id.message_text)
        textView.text = messages[position]

        return rowView
    }
}