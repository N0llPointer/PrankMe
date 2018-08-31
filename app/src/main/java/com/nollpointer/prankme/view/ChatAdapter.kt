package com.nollpointer.prankme.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.nollpointer.prankme.Message
import com.nollpointer.prankme.R
import java.text.SimpleDateFormat


class ChatAdapter(context: Context,var messages: Array<Message>): ArrayAdapter<Message>(context,-1,messages) {

    val timeFormat = SimpleDateFormat("HH:mm")
    val sounds = mapOf(0 to "Dab",1 to "Fart",2 to "Bong",3 to "Click")

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView =if(messages[position].isMine)
            inflater.inflate(R.layout.message_mine_layout, parent, false)
        else
            inflater.inflate(R.layout.message_stranger_layout, parent, false)
        var textView: TextView = rowView.findViewById(R.id.message_text)
        textView.text = sounds.get(messages[position].soundId)

        textView = rowView.findViewById(R.id.message_time)
        textView.text = timeFormat.format(messages[position].time).toString()
        return rowView
    }


    override fun getCount(): Int {
        return messages.size
    }
}