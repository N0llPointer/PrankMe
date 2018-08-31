package com.nollpointer.prankme.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.nollpointer.prankme.R
import com.nollpointer.prankme.Sound

class SoundPickerAdapter(context:Context,var sounds: List<Sound>): ArrayAdapter<Sound>(context,-1,sounds) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.sound_picker_card, parent, false)
        var textView: TextView = rowView.findViewById(R.id.sound_name)
        textView.text = sounds.get(position).name

        rowView.setBackgroundColor(Color.DKGRAY)

        return rowView
    }

    //    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val cardView = holder.customCardView
//        var textView: TextView = cardView.findViewById(R.id.sound_name)
//        textView.text = sounds.get(position).name
//        cardView.setOnClickListener{
//            listener.onClick(position)
//        }
//        cardView.setCardBackgroundColor(Color.BLACK)
//    }
//

}