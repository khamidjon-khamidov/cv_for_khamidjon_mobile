package com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.models.about_me.AboutMeModel
import com.hamidjonhamidov.whoiskhamidjon.ui.displaySnackbar
import com.hamidjonhamidov.whoiskhamidjon.ui.main.DateUtil
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainActivity
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeStateEvent.*
import com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me.PersonalInfo
import com.hamidjonhamidov.whoiskhamidjon.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_about_me.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlin.math.hypot
import kotlin.math.max

class AboutMeFragment : BaseAboutMeFragment(), BackPressForAboutMe {

    private val TAG = "AppDebug"

    companion object {
        const val SHOULD_CLOSE_PHOTO = true
        const val SHOULD_NOT_CLOSE_PHOTO = false

        var mCurrentState = SHOULD_NOT_CLOSE_PHOTO
    }

    lateinit var mBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    lateinit var job: CompletableJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "AboutMeFragment: onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "AboutMeFragment: onViewCreated: ")

        setHasOptionsMenu(true)
        setActionBarTitle("About Me")

        initilaizeEverything()

        if (viewModel.viewState.value?.aboutMeFields?.aboutMeModel == null) {
            subscribeObservers()
            viewModel.setStateEvent(GetAboutMeStateEvent())
        } else {
            updateView(viewModel.viewState.value!!.aboutMeFields.aboutMeModel!!)
        }

    }

    fun initilaizeEverything(){
        setLeftDrawerListeners()
        initializeBottomsheet()
        initializeExperiencePeriod()
        initializeCircularReveal()

        // set listener in the main activity
        (activity as MainActivity?)?.setMOnBackPressListener(this)
    }

    private fun initializeCircularReveal() {
        val circularExitAnLay = activity?.findViewById<View>(R.id.iv_exit_circular)

        btn_profile_img_about_me?.setOnClickListener {
            openCircularReveal()
        }

        circularExitAnLay?.setOnClickListener {
            closeCircularReveal()
        }
    }

    private fun openCircularReveal(){
        val circularEnterAnLay = activity?.findViewById<View>(R.id.cl_circular_animation)

        val x = id_about_me_fragment?.right
        val y = id_about_me_fragment?.left

        val startRadius = 0.toFloat()
        val endRadius = hypot(
            x = id_about_me_fragment!!.width.toDouble(),
            y = id_about_me_fragment!!.height.toDouble()
        ).toFloat()

        val animator =
            ViewAnimationUtils.createCircularReveal(
                circularEnterAnLay,
                x!!,
                y!!,
                startRadius,
                endRadius
            )

        mCurrentState = SHOULD_CLOSE_PHOTO
        circularEnterAnLay?.visibility = View.VISIBLE
        animator.start()

    }

    private fun closeCircularReveal(){

        val circularEnterAnLay = activity?.findViewById<View>(R.id.cl_circular_animation)

        val x = id_about_me_fragment?.right
        val y = id_about_me_fragment?.left

        cl_circular_animation?.setOnClickListener {}

        val startRadius =  max(id_about_me_fragment!!.width, id_about_me_fragment!!.height).toFloat()
        val endRadius = 0.toFloat()

        val animator =
            ViewAnimationUtils.createCircularReveal(
                circularEnterAnLay,
                x!!,
                y!!,
                startRadius,
                endRadius
            )

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)
            }

            override fun onAnimationEnd(animation: Animator?) {
                circularEnterAnLay?.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                super.onAnimationStart(animation, isReverse)
            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })

        mCurrentState = SHOULD_NOT_CLOSE_PHOTO
        animator.start()
    }

    override fun onResume() {
        super.onResume()
        mainStateChangeListener.shouldStartShimmerInFragment(true)
        MainNavigation.setSelected(activity!! as MainActivity, R.id.menu_item_about_me)

    }

    override fun onPause() {
        super.onPause()
        mainStateChangeListener.shouldStartShimmerInFragment(false)
    }

    private fun initializeBottomsheet() {
        // this is for bottomSheet
        mBottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_about_me)
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        tv_education_info_about_me_tuit.setOnClickListener {
            viewModel.viewState.value?.aboutMeFields?.aboutMeModel?.let {
                tv_uni_name_about_me_btm_sheet.setText(Constants.TUIT)

                // bind subject scores and subjects
                val subject = it.convertStrToSubject(it.tuit)
                tv_btm_score_list_about_me.setText(subject.score)
                tv_btm_subject_list_about_me.setText(subject.subject)

                mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } ?: let {
                activity?.displaySnackbar(
                    id_about_me_fragment,
                    Constants.NO_DATA_AVAILABLE,
                    "Retry",
                    object : OnSnackbarClicked {
                        override fun onSnackbarClick(snackbar: Snackbar) {
                            viewModel.setStateEvent(GetAboutMeStateEvent())
                        }
                    })
            }
        }

        tv_education_info_about_me_lsbu.setOnClickListener {
            viewModel.viewState.value?.aboutMeFields?.aboutMeModel?.let {
                tv_uni_name_about_me_btm_sheet.setText(Constants.LSBU)

                // bind subject scores and subjects
                val subject = it.convertStrToSubject(it.south_bank)
                tv_btm_score_list_about_me.setText(subject.score)
                tv_btm_subject_list_about_me.setText(subject.subject)

                mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } ?: let {
                activity?.displaySnackbar(
                    id_about_me_fragment,
                    Constants.NO_DATA_AVAILABLE,
                    "Retry",
                    object : OnSnackbarClicked {
                        override fun onSnackbarClick(snackbar: Snackbar) {
                            viewModel.setStateEvent(GetAboutMeStateEvent())
                        }
                    })
            }

        }
    }

    private fun subscribeObservers() {

        Log.d(TAG, "AboutMeFragment: subscribeObservers: 1")

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            // DataState<AboutMeViewState>

            Log.d(TAG, "AboutMeFragment: subscribeObservers: 2")

            if (dataState != null) {

                dataState.data?.dataReceived?.getContentIfNotHandled()?.aboutMeFields?.let {
                    Log.d(
                        TAG,
                        "AboutMeFragment: subscribeObservers: aboutMeFields:vo ${it.aboutMeModel}"
                    )
                    viewModel.setAboutMeFields(it)
                    stateChangeListener.onDataStateChange(dataState)
                }
            }
        })


        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if (viewState != null) {

                Log.d(TAG, "AboutMeFragment: subscribeObservers: 3")

                viewState.aboutMeFields.aboutMeModel?.let { aboutMeModel ->
                    updateView(aboutMeModel)
                    updatePersonalInfo(aboutMeModel)
                }
            }

        })
    }

    private fun updateView(aboutMeModel: AboutMeModel) {

        dependencyProvider.getGlideRequestManager()
            .load(aboutMeModel.profile_image_url)
            .into(iv_profile_picture_about_me)

        dependencyProvider.getGlideRequestManager()
            .load(aboutMeModel.profile_image_url)
            .into(activity?.findViewById(R.id.iv_circular_image_itself)!!)

        tv_address_info_about_me.setText(aboutMeModel.address)
        tv_phone_info_about_me.setText(aboutMeModel.phone_number)
        tv_email_info_about_me.setText(aboutMeModel.email)
    }

    fun updatePersonalInfo(aboutMeModel: AboutMeModel){
        PersonalInfo.phoneNumber = aboutMeModel.phone_number
        PersonalInfo.email = aboutMeModel.email
    }

    private fun initializeExperiencePeriod() {
        job = Job()
        val coroutineScope = CoroutineScope(IO + job)
        coroutineScope.launch {
            while (true) {
                withContext(Main) {
                    tv_pr_ex_info_about_me?.setText(DateUtil.getDifferenceWithCurrentDate())
                }
                delay(1000)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_refresh, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.menu_item_refresh -> {
                viewModel.setStateEvent(GetAboutMeStateEvent())
                return true
            }
        }

        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mCurrentState = SHOULD_NOT_CLOSE_PHOTO

        job.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "AboutMeFragment: onDestroy: ")
    }

    override fun onBackPress() {
        if(mCurrentState == SHOULD_CLOSE_PHOTO){
            closeCircularReveal()
        }
    }
}

interface BackPressForAboutMe{
    fun onBackPress()
}
























