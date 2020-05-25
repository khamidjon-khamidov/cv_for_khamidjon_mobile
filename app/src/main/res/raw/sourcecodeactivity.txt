package com.hamidjonhamidov.cvforkhamidjon.ui.c_source_code

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.ui.c_source_code.custom_treeview.Constants
import com.hamidjonhamidov.cvforkhamidjon.ui.c_source_code.custom_treeview.FileView
import com.hamidjonhamidov.cvforkhamidjon.ui.c_source_code.custom_treeview.UtilFunctions
import kotlinx.android.synthetic.main.activity_source_code.*
import java.util.*

class SourceCodeActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "AppDebug"

    val hashMap = TreeMap<Int, FileView>()

    var utilFuncs = UtilFunctions()

    lateinit var root: FileView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source_code)

        setSupportActionBar(tool_bar_source_code)
        supportActionBar?.title = "Source Code"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initializeFileHierarchy()

    }

    private fun initializeFileHierarchy() {
        root = FileView(
            type = Constants.FILE_TYPE_BLUE_FILE,
            name = "root",
            parent = null,
            containerCl = cl_root_file_container,
            childClMain = cl_file_view,
            expandIv = iv_expand,
            fileTypeIv = iv_file_type,
            fileTextTv = tv_file_name
        )

        cl_file_view.setOnClickListener(this)
        hashMap[R.id.cl_file_view] = root

        utilFuncs.createHierarchyFromStringList(root, StaticData.leafFileList)

        // when view is created, open certain files to look it beautiful
        onClick(root.childClMain)
        onClick(root.children[0].childClMain)
    }

    override fun onClick(v: View?) {
        if (v == null) return

        Log.d(TAG, "SourceCodeActivity: onClick: called")

        var clickedItem: FileView? = null
        if (hashMap.containsKey(v.id))
            clickedItem = hashMap[v.id]

        if (clickedItem == null) return

        workWithClickedItem(clickedItem)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.source_code_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

            android.R.id.home -> {
                finish()
            }

            R.id.menu_expand_all_source_code -> {
                expandReduce(true, root)
            }

            R.id.menu_reduce_all_source_code -> {
                expandReduce(false, root)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}








