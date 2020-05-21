package com.hamidjonhamidov.whoiskhamidjon.ui.main.persojal_projects

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainDataStateChangeListener
import com.hamidjonhamidov.whoiskhamidjon.util.DataStateChangeListener
import com.hamidjonhamidov.whoiskhamidjon.util.setActionBarTitle
import com.hamidjonhamidov.whoiskhamidjon.util.setLeftDrawerListeners
import com.skyhope.showmoretextview.ShowMoreTextView
import kotlinx.android.synthetic.main.fragment_personal_projects.*

class PersonalProjectsFragment : Fragment() {

    private val TAG = "AppDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "PersonalProjectsFragment: onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setActionBarTitle("My Personal Projects")

        (activity as MainDataStateChangeListener).shouldStartShimmerInFragment(false)

        initialize()
        updateView()
    }

    fun updateView() {
        val tv1 = tv_pr1_show_more_personal_projects
        val tv2 = tv_pr2_show_more_personal_projects
        val tv3 = tv_pr3_show_more_personal_projects

        updateShowMoreTv(
            tv1,
            btn_show_more_1,
            "https://play.google.com/store/apps/details?id=${context?.packageName}"
        )
        updateShowMoreTv(
            tv2,
            btn_show_more_2,
            "https://play.google.com/store/apps/details?id=com.hamidovhamid1998.calculator"
        )
        updateShowMoreTv(
            tv3,
            btn_show_more_3,
            "https://play.google.com/store/apps/details?id=com.hamidovhamidjondictionary.englishuzbekdictionary100k"
        )
    }

    private fun updateShowMoreTv(tv: ShowMoreTextView?, btn: Button?, link: String) {
        tv?.addShowMoreText("Read more")
        tv?.addShowLessText("Read less")
        tv?.setShowingLine(2)
        tv?.setShowMoreColor(Color.BLUE)
        tv?.setShowLessTextColor(Color.BLUE)

        btn?.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(link));
            startActivity(i)
        }
    }


    fun initialize() {
        setLeftDrawerListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "PersonalProjectsFragment: onDestroy: ")
    }
}
