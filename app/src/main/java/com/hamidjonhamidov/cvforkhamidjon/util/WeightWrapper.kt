package com.hamidjonhamidov.cvforkhamidjon.util

import android.view.View
import android.widget.LinearLayout

class WeightWrapper (val view: View){

    fun setWeight(weight: Float){
        val params = view.layoutParams as LinearLayout.LayoutParams
        params.weight = weight
        view.parent.requestLayout()
    }

    fun getWeight(): Float = (view.layoutParams as LinearLayout.LayoutParams).weight
}