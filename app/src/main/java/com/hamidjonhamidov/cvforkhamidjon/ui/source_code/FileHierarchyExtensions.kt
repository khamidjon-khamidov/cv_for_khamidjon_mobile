package com.hamidjonhamidov.cvforkhamidjon.ui.source_code

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.ui.source_code.custom_treeview.Constants
import com.hamidjonhamidov.cvforkhamidjon.ui.source_code.custom_treeview.CreateViews
import com.hamidjonhamidov.cvforkhamidjon.ui.source_code.custom_treeview.FileView

fun SourceCodeActivity.workWithClickedItem(mFileView: FileView) {

    val TAG = "AppDebug"
    Log.d(TAG, "SourceCodeActivity: workWithClickedItem: clicked")

    // if file is leaf open it
    if (mFileView.type == Constants.FILE_TYPE_LEAF) {

        mFileView.rawId?.let {
            val mIntent = Intent(this, CodeViewActivity::class.java)
            mIntent.putExtra(Constants.RES_ID_EXTRA, it)
            startActivity(mIntent)
        }

        return
    }

    // if file is open close it
    if (mFileView.openCloseState == Constants.STATE_OPENED_IN_VIEW) {
        Log.d(TAG, "SourceCodeActivity: workWithClickedItem: trying to close file: ${mFileView.name}")

        mFileView.childClSec!!.visibility =
            View.GONE

        mFileView.openCloseState = Constants.STATE_CLOSED_IN_VIEW
        mFileView.expandIv?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.expand_default))

        return
    }

    // if file is closed, open it
    if (mFileView.openCloseState == Constants.STATE_CLOSED_IN_VIEW) {

        // if child container second hasn't been created, create it with its children
        if (mFileView.childClSec == null) {

            // create container second, and connect it to parent and childContainerMain
            mFileView.childClSec = CreateViews.createConstraintLayout(this, 30, 0, 0, 0)

            // connect child sec to parent and child main
            CreateViews.connectCl1OnBottomCl2InsideTopCl3(
                mFileView.childClSec!!,
                mFileView.childClMain!!,
                mFileView.containerCl!!
            )

            // child container created now time to add children
            var itemTopCl = mFileView.childClMain!!

            for (childFile in mFileView.children) {

                Log.d(TAG, "SourceCodeActivity: workWithClickedItem: childName=${childFile.name}, state=${childFile.children.size}")

                // main cl for child file
                childFile.childClMain = createChildMainWithChildren(childFile)

                // root cl for child simple file
                childFile.containerCl = CreateViews.createConstraintLayout(this, 20, 0, 0, 0)

                // connect childMain To child root container
                CreateViews.connectMainToParent(
                    childFile.childClMain!!,
                    childFile.containerCl!!
                )

                // connect child root to mFile root and top container
                CreateViews.connectCl1OnBottomCl2InsideTopCl3(
                    childFile.containerCl!!,
                    itemTopCl,
                    mFileView.childClSec!!
                )


                // save item so that next item is placed on bottom of previous item
                itemTopCl = childFile.containerCl!!

                // save parent to object
                childFile.parent = mFileView

                // set  listener
                childFile.childClMain!!.setOnClickListener(this)
                // save this object for future use
                hashMap[childFile.childClMain!!.id] = childFile
            }

        }

        // show the container
        mFileView.childClSec!!.visibility = View.VISIBLE
        mFileView.openCloseState = Constants.STATE_OPENED_IN_VIEW
        mFileView.expandIv?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.expand_down))
    }

}

fun SourceCodeActivity.createChildMainWithChildren(childFile: FileView): ConstraintLayout {
    // create children inside childMainContainer
    childFile.expandIv =
        CreateViews.createImageViewForExpand(this, R.drawable.expand_default, 30)

    if(childFile.type == Constants.FILE_TYPE_LEAF){
        childFile.expandIv?.setImageDrawable(resources.getDrawable(R.drawable.dot))
    }

    childFile.fileTypeIv = CreateViews.createImageViewForFileType(
        this,
        when (childFile.type) {
            Constants.FILE_TYPE_BLUE_FILE -> R.drawable.file_type_blue_folder
            Constants.FILE_TYPE_PACKAGE -> R.drawable.file_type_package
            else -> R.drawable.file_type_leaf
        }
    )

    childFile.fileTextTv = CreateViews.createTextViewForFileName(
        this,
        childFile.name,
        if (childFile.type == Constants.FILE_TYPE_LEAF) R.color.blue else R.color.black,
        10
    )

    // put children views to new childContainerMain
    return CreateViews.createContainerMain(
        this,
        childFile.expandIv!!,
        childFile.fileTypeIv!!,
        childFile.fileTextTv!!
    )
}


fun SourceCodeActivity.expandReduce(shouldExpand: Boolean, root: FileView?){
    if(root == null || root.type == Constants.FILE_TYPE_LEAF) return

    // if the children of current view is not
    // created and the function is reducing all,
    // then just return. no need to create again
    if(!shouldExpand && root.childClSec==null) return

    root.openCloseState = if(shouldExpand){
        Constants.STATE_CLOSED_IN_VIEW
    } else {
        Constants.STATE_OPENED_IN_VIEW
    }

    onClick(root.childClMain)

    for(child in root.children){
        expandReduce(shouldExpand, child)
    }
}
