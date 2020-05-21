package com.hamidjonhamidov.cvforkhamidjon.ui.source_code

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.tiagohm.codeview.CodeView
import br.tiagohm.codeview.Language
import br.tiagohm.codeview.Theme
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.ui.source_code.custom_treeview.Constants.RES_ID_EXTRA
import kotlinx.android.synthetic.main.activity_code_view.*
import java.io.ByteArrayOutputStream

class CodeViewActivity : AppCompatActivity(), CodeView.OnHighlightListener {

    private val TAG = "AppDebug"

    var resId: Int = 0
    lateinit var codeStr: String

    lateinit var language: Language

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_view)

        resId = intent.getIntExtra(RES_ID_EXTRA, -1)

        if (resId == -1) finish()

        getCode()
        initCodeVeiw()
    }

    private fun initCodeVeiw() {
        codeView.setOnHighlightListener(this)
            .setOnHighlightListener(this)
            .setTheme(Theme.ANDROIDSTUDIO)
            .setCode(codeStr)
            .setLanguage(Language.KOTLIN)
            .setWrapLine(false)
            .setFontSize(14f)
            .setZoomEnabled(true)
            .setShowLineNumber(true)
            .setStartLineNumber(1)
            .apply()
    }

    private fun getCode() {
        val inputStream = resources.openRawResource(resId)

        val byteArrayOutputStream = ByteArrayOutputStream()

        var i: Int
        try {
            i = inputStream.read()
            while (i != -1) {
                byteArrayOutputStream.write(i)
                i = inputStream.read()
            }
            inputStream.close()
        } catch (e: Exception) {
            codeStr = ""
        }

        codeStr = byteArrayOutputStream.toString()
        language =
            if (codeStr.contains("<?xml version=\"1.0\" encoding=\"utf-8\"?>")) Language.XML
            else Language.KOTLIN

        Log.d(TAG, "CodeViewActivity: getCode: $codeStr")
    }

    override fun onStartCodeHighlight() {

    }

    override fun onLanguageDetected(p0: Language?, p1: Int) {

    }

    override fun onFontSizeChanged(p0: Int) {

    }

    override fun onLineClicked(p0: Int, p1: String?) {

    }

    override fun onFinishCodeHighlight() {

    }
}
