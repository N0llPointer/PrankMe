package com.nollpointer.prankme

import android.view.View
import android.widget.LinearLayout

class PickSoundWrapper(val view: View) {
    init {
        if(view.layoutParams !is LinearLayout.LayoutParams)
            throw IllegalArgumentException("View must have LinearLayout as parent!")
    }

    var weight: Float = 0f
    set(value) {
        val params = view.layoutParams as LinearLayout.LayoutParams
        params.weight = value
        view.parent.requestLayout()
    }

}