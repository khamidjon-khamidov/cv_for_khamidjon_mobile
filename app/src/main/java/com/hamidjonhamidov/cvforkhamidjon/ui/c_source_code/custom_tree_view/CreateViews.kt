package com.hamidjonhamidov.cvforkhamidjon.ui.c_source_code.custom_treeview

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat

object CreateViews {

    private val TAG = "AppDebug"

    // connect childContainerSec to childContainerMain and parent
    fun connectCl1OnBottomCl2InsideTopCl3(
        c1: ConstraintLayout,
        c2: ConstraintLayout,
        c3: ConstraintLayout
    ) {

        // add Sec child
        c3.addView(c1)

        val clSet = ConstraintSet()
        clSet.clone(c3)

        clSet.connect(c1.id, ConstraintSet.TOP, c2.id, ConstraintSet.BOTTOM)
        clSet.connect(c1.id, ConstraintSet.LEFT, c3.id, ConstraintSet.LEFT)

        clSet.applyTo(c3)
    }

    // connect childContainerMain and childContainerSec to their parent
    fun connectMainToParent(
        childMainContainer: ConstraintLayout,
        parent: ConstraintLayout
    ) {

        // add Main first child
        parent.addView(childMainContainer)

        val clSet = ConstraintSet()
        clSet.clone(parent)

        clSet.connect(childMainContainer.id, ConstraintSet.TOP, parent.id, ConstraintSet.TOP)
        clSet.connect(childMainContainer.id, ConstraintSet.LEFT, parent.id, ConstraintSet.LEFT)

        clSet.applyTo(parent)

        Log.d(TAG, "CreateViews: connectMainToParent: called")
    }

    // create constraint layout with wrap_content params, and margins
    fun createConstraintLayout(
        activity: Activity,
        leftMargin: Int,
        rightMargin: Int,
        topMargin: Int,
        bottomMargin: Int
    ): ConstraintLayout {
        val cl = ConstraintLayout(activity)
        cl.id = View.generateViewId()


        val layParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layParams.leftMargin = leftMargin
        layParams.rightMargin = rightMargin
        layParams.topMargin = topMargin
        layParams.bottomMargin = bottomMargin

        cl.layoutParams = layParams

        Log.d(TAG, "CreateViews: createConstraintLayout: called")
        
        return cl

    }

    // create childContainerMain with expandIv, fileTypeIv, fileNameTv
    fun createContainerMain(
        activity: Activity,
        expandIv: ImageView,
        fileTypeIv: ImageView,
        fileTxtTv: TextView
//        childContainerCl: ConstraintLayout? = null
    ): ConstraintLayout {

        // initialize constraint layout
        val cl = ConstraintLayout(activity)
        cl.id = View.generateViewId()

        cl.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // adding expand image view
        cl.addView(expandIv)
        // adding file type image view
        cl.addView(fileTypeIv)
        // adding file name text view
        cl.addView(fileTxtTv)


        val clSet = ConstraintSet()
        clSet.clone(cl)

        // binding expand iv
        clSet.connect(expandIv.id, ConstraintSet.TOP, cl.id, ConstraintSet.TOP)
        clSet.connect(expandIv.id, ConstraintSet.LEFT, cl.id, ConstraintSet.LEFT)
        clSet.connect(expandIv.id, ConstraintSet.BOTTOM, cl.id, ConstraintSet.BOTTOM)

        // binding file type iv
        clSet.connect(fileTypeIv.id, ConstraintSet.LEFT, expandIv.id, ConstraintSet.RIGHT)
        clSet.connect(fileTypeIv.id, ConstraintSet.TOP, cl.id, ConstraintSet.TOP)
        clSet.connect(fileTypeIv.id, ConstraintSet.BOTTOM, cl.id, ConstraintSet.BOTTOM)

        // binding file text tv
        clSet.connect(fileTxtTv.id, ConstraintSet.LEFT, fileTypeIv.id, ConstraintSet.RIGHT)
        clSet.connect(fileTxtTv.id, ConstraintSet.TOP, cl.id, ConstraintSet.TOP)
        clSet.connect(fileTxtTv.id, ConstraintSet.BOTTOM, cl.id, ConstraintSet.BOTTOM)

        clSet.applyTo(cl)

        Log.d(TAG, "CreateViews: createContainerMain: called")
        return cl
    }

    // file name text view
    fun createTextViewForFileName(
        activity: Activity,
        txt: String,
        @ColorRes colorId: Int,
        leftMargin: Int
    ): TextView {

        val fileNameTv = TextView(activity.applicationContext)
        fileNameTv.id = View.generateViewId()

        val layParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layParams.leftMargin = leftMargin
//        layParams.pa = 10

        fileNameTv.layoutParams = layParams
        fileNameTv.setPadding(0, 0, 25, 0)

        fileNameTv.text = txt
        fileNameTv.textSize = 22f
        fileNameTv.setTextColor(activity.resources.getColor(colorId))

        Log.d(TAG, "CreateViews: createTextViewForFileName: ")
        
        return fileNameTv
    }

    // file type image view
    fun createImageViewForFileType(activity: Activity, @DrawableRes iconId: Int): ImageView {
        val fileTypeIv = ImageView(activity.applicationContext)
        fileTypeIv.layoutParams =
            ViewGroup.LayoutParams(
                80,
                80
            )

        fileTypeIv.id = View.generateViewId()

        fileTypeIv.setImageDrawable(ContextCompat.getDrawable(activity, iconId))

        return fileTypeIv
    }

    // expand image view
    fun createImageViewForExpand(
        activity: Activity,
        @DrawableRes iconId: Int,
        leftMargin: Int
    ): ImageView {
        val expandIv = ImageView(activity.applicationContext)
        expandIv.id = View.generateViewId()

        val layParams = ConstraintLayout.LayoutParams(
            60,
            60
        )

        layParams.leftMargin = leftMargin

        expandIv.layoutParams = layParams

        expandIv.setImageDrawable(ContextCompat.getDrawable(activity, iconId))

        Log.d(TAG, "CreateViews: createImageViewForExpand: imageViewCreated")

        return expandIv
    }

}






















