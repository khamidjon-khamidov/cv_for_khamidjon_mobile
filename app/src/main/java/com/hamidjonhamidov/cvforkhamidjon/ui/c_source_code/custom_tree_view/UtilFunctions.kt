package com.hamidjonhamidov.cvforkhamidjon.ui.c_source_code.custom_treeview

import android.util.Log
import com.hamidjonhamidov.cvforkhamidjon.ui.c_source_code.custom_treeview.Constants.FILE_TYPE_LEAF
import com.hamidjonhamidov.cvforkhamidjon.ui.c_source_code.custom_treeview.Constants.FILE_TYPE_PACKAGE

data class PathFile(val paths: List<String>, val resId: Int)

class UtilFunctions {

    private val TAG = "AppDebug"

    fun createHierarchyFromStringList(root: FileView, hierList: List<PathFile>) {

        for (eachPath in hierList) {

            var parent = root

            for (childName in eachPath.paths) {
                var child = returnChild(childName, parent)
                if (child == null) {
                    child = FileView(name = childName, type = FILE_TYPE_PACKAGE)
                    parent.children.add(child)

                    Log.d(TAG, "UtilFunctions: createHierarchyFromStringList: ${childName}")
                }

                parent = child
            }

            Log.d(TAG, "UtilFunctions: createHierarchyFromStringList: name := ${parent.name}")
            parent.type = FILE_TYPE_LEAF
            parent.rawId = eachPath.resId
        }
    }


    private fun returnChild(childName: String, parent: FileView): FileView? {
        for (child in parent.children) {
            if (child.name == childName)
                return child
        }

        return null
    }
}
