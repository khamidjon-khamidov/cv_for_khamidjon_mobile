package com.hamidjonhamidov.cvforkhamidjon.ui

import android.app.Activity
import android.app.Dialog
import android.content.pm.PackageManager
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.hamidjonhamidov.cvforkhamidjon.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.info_dialog.*
import kotlinx.coroutines.*

private val TAG = "AppDebug"

fun Activity.showMyDialog(title: String, description: String, action: () -> Unit) {
    val dialog = Dialog(this)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.info_dialog)
    dialog.dialog_btn_ok.setOnClickListener {
        action.invoke()
        dialog.dismiss()
    }

    dialog.dialog_tv_main_info.text = title
    dialog.dialog_tv_text_des.text = description
    dialog.show()
}

fun Activity.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Activity.showProgressBar(isShow: Boolean) {
    progress_bar?.visibility = if (isShow) View.VISIBLE else View.GONE
}

fun Activity.askPermission(
    permissionName: String,
    permissionCode: Int
): Boolean {

    if (ContextCompat.checkSelfPermission(this, permissionName)
        == PackageManager.PERMISSION_GRANTED
    )
        return true

    ActivityCompat.requestPermissions(
        this,
        arrayOf(permissionName),
        permissionCode
    )

    return false
}

fun Activity.delayInBackgLaunchInMain(scope: CoroutineScope, timeDelay: Long, action: () -> Unit) {
    scope.launch(Dispatchers.Default) {
        delay(timeDelay)
        withContext(Dispatchers.Main) {
            action.invoke()
        }
    }
}



















