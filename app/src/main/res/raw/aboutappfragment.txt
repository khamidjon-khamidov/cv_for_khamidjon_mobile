package com.hamidjonhamidov.whoiskhamidjon.ui.main.about_app


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.util.setLeftDrawerListeners

class AboutAppFragment : Fragment() {

    private val TAG = "AppDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "AboutAppFragment: onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    fun initialize(){
        setLeftDrawerListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "AboutAppFragment: onDestroy: ")
    }

}
