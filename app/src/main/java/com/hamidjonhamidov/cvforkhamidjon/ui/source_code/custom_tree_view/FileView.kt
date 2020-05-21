package com.hamidjonhamidov.cvforkhamidjon.ui.source_code.custom_treeview

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.ui.source_code.custom_treeview.Constants.FILE_TYPE_BLUE_FILE
import com.hamidjonhamidov.cvforkhamidjon.ui.source_code.custom_treeview.Constants.FILE_TYPE_PACKAGE
import com.hamidjonhamidov.cvforkhamidjon.ui.source_code.custom_treeview.Constants.STATE_CLOSED_IN_VIEW

data class FileView(
    var type: Int = FILE_TYPE_PACKAGE,
    val name: String,
    var parent: FileView? = null,
    var rawId: Int? = null,
    val children: ArrayList<FileView> = ArrayList(),
    var openCloseState: Int = STATE_CLOSED_IN_VIEW,
    var containerCl: ConstraintLayout? = null,
    var childClMain: ConstraintLayout? = null,
    var childClSec: ConstraintLayout? = null,
    var expandIv: ImageView? = null,
    var fileTypeIv: ImageView? = null,
    var fileTextTv: TextView? = null
){
    fun FileView.getFileFromId(viewId: Int, children: ArrayList<FileView>)
            : FileView? {

        for (child in children) {
            if (viewId == child.containerCl?.id) {
                return child
            }
        }

        return null
    }

    fun FileView.getIconFromFileType(fileType: Int) =
        when (fileType) {
            FILE_TYPE_BLUE_FILE -> {
                R.drawable.file_type_blue_folder
            }

            Constants.FILE_TYPE_PACKAGE -> {
                R.drawable.file_type_package
            }

            Constants.FILE_TYPE_LEAF -> {
                R.drawable.file_type_leaf
            }

            else -> {
                R.drawable.file_type_package
            }
        }

}













