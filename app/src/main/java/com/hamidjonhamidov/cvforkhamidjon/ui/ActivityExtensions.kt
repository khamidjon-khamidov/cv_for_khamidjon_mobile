package com.hamidjonhamidov.cvforkhamidjon.ui

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.widget.Toast
import com.hamidjonhamidov.cvforkhamidjon.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.info_dialog.*

fun Activity.showDialog(title: String, description: String, action: ()->Unit){
    val dialog = Dialog(this)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.info_dialog)
    dialog.dialog_btn_ok.setOnClickListener {
        action.invoke()
        dialog.cancel()
    }
    dialog.dialog_tv_main_info.text = title
    dialog.dialog_tv_text_des.text = description
    dialog.show()
}

fun Activity.showToast(text: String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Activity.showProgressBar(isShow: Boolean) {
    progress_bar?.visibility = if (isShow) View.VISIBLE else View.GONE
}