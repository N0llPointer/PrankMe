package com.nollpointer.prankme

data class Message(val soundId:Int,val time: Long, val isMine: Boolean, var isRecieved: Boolean)