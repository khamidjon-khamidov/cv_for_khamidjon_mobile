package com.hamidjonhamidov.cvforkhamidjon.ui.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.ui.delayInBackgLaunchInMain
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.*
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.showMyDialog
import com.hamidjonhamidov.cvforkhamidjon.ui.showProgressBar
import com.hamidjonhamidov.cvforkhamidjon.ui.showToast
import com.hamidjonhamidov.cvforkhamidjon.util.StateEvent
import com.hamidjonhamidov.cvforkhamidjon.util.UIType
import kotlinx.coroutines.*

@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
abstract class BaseMainFragment<T>(
    fragmentId: Int,
    private val viewModelFactory: ViewModelProvider.Factory,
    val stateEvent: StateEvent
) : Fragment(fragmentId) {

    private val TAG = "AppDebug"

    private lateinit var whichFragment:String

    val viewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        whichFragment = stateEvent.whichFragment
        subscribeDataObservers()
        subscribeNewMessageObserver()
        subscribeProgressBar()
        initData()
    }

    override fun onResume() {
        super.onResume()
        activity?.showProgressBar(viewModel.getProgressBarStatus(whichFragment))
        requireActivity().delayInBackgLaunchInMain(lifecycleScope, 500) {
            processNextMessage()
        }
    }

    override fun onStop() {
        super.onStop()
        activity?.showProgressBar(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

//        return when(item.itemId){
//            R.id.home_mi_refresh -> {
//                viewModel.setStateEvent(stateEvent as MainStateEvent)
//                true
//            }
//
//            else ->
                return super.onOptionsItemSelected(item)
//        }

    }

    fun subscribeProgressBar(){
        viewModel.getProgressBarObserver(whichFragment).observe(viewLifecycleOwner, Observer {
            activity?.showProgressBar(it)
        })
    }

    fun subscribeNewMessageObserver() {
        // observe new message
        viewModel.newMessageNotifier.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "BaseMainFragment: subscribeNewMessageObserver: new message came")
            requireActivity().delayInBackgLaunchInMain(lifecycleScope, 500) {
                processNextMessage()
            }
        })
    }

    fun processNextMessage() {

        Log.d(TAG, "BaseMainFragment: processNextMessage: $whichFragment, size = ${viewModel.getMessagesSize(whichFragment)}")
        // if there is any dialog that is being processed, just ignore this
        if (viewModel.isLastMessageInProgress(whichFragment)){
            Log.d(
                TAG,
                "BaseMainFragment: processNextMessage: returned cos of last message in progress message = ${viewModel.getLastMessage(whichFragment)}"
            )
            return
        }

        val newMessage = viewModel.getLastMessage(whichFragment) ?: return

        Log.d(TAG, "BaseMainFragment: processNextMessage: $newMessage")

        when (newMessage.message.uiType) {
            is UIType.Dialog -> {
                activity?.showMyDialog(
                    newMessage.message.title,
                    newMessage.message.description
                ) {
                    Log.d(TAG, "BaseMainFragment: processNextMessage: OK pressed")
                    viewModel.removeLastMessage(whichFragment)

                    // wait for a second to proccess next message
                    requireActivity().delayInBackgLaunchInMain(lifecycleScope, 500) {
                        processNextMessage()
                    }
                }
                viewModel.setLastMessageToProgress(whichFragment)
            }

            is UIType.Toast -> {
                activity?.showToast(newMessage.message.description)
                viewModel.removeLastMessage(whichFragment)
            }
        }

    }

    abstract fun subscribeDataObservers()

    abstract fun initData()

    abstract fun updateView(myModel: T)

}