package com.nollpointer.prankme.view

import android.content.Context
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nollpointer.prankme.R
import com.nollpointer.prankme.Sound

class SoundMessageView(context: Context, val sound: Sound): CardView(context){

    init {

        val inflater = LayoutInflater.from(context) as LayoutInflater
        inflater.inflate(R.layout.sound_message_cardview,this)

        val textView: TextView = findViewById(R.id.sound_message_text)
        textView.text = sound.name

        findViewById<View>(R.id.sound_message_clear).setOnClickListener {
            val parent = parent as ViewGroup
            parent.removeView(this)
        }
    }


}