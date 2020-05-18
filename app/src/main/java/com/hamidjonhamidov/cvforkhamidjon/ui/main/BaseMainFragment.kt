package com.hamidjonhamidov.cvforkhamidjon.ui.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.ui.delayInBackgLaunchInMain
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.*
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewDestEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.showMyDialog
import com.hamidjonhamidov.cvforkhamidjon.ui.showProgressBar
import com.hamidjonhamidov.cvforkhamidjon.ui.showToast
import com.hamidjonhamidov.cvforkhamidjon.util.UIType
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_ALREADY_IN_PROGRESS
import com.hamidjonhamidov.cvforkhamidjon.util.data_manager.InboxManager
import com.hamidjonhamidov.cvforkhamidjon.util.data_manager.UIMessage
import kotlinx.coroutines.*

@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
abstract class BaseMainFragment<T>(
    fragmentId: Int,
    private val viewModelFactory: ViewModelProvider.Factory,
    val stateEvent: MainStateEvent
) : Fragment(fragmentId) {

    private val TAG = "AppDebug"

    val viewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    val progressBarObserver = Observer<Boolean> {
        activity?.showProgressBar(it)
    }

    val messageObserverForProgressBar = Observer<UIMessage> {
        if (!(it.equals(MESSAGE_ALREADY_IN_PROGRESS))) {
            viewModel.inboxManager.setProgressBarStateAndNotify(stateEvent.destinationView, false)
        }
    }


    val newMessageObserver = Observer<UIMessage> {
        if (inboxManager.getInboxSize(stateEvent.destinationView) > 0)
            processNextMessage()
    }

    val messageNotifierLiveData: LiveData<UIMessage> by lazy {
        viewModel.inboxManager.getMessagesNotifer(stateEvent.destinationView)
    }

    val inboxManager: InboxManager<MainViewDestEvent> by lazy {
        viewModel.inboxManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        addProgressBarObservers()
        subscribeDataObservers()
        subscribeNewMessageObserver()
        initData()
    }

    override fun onResume() {
        super.onResume()
        activity?.showProgressBar(inboxManager.getProgressBarState(stateEvent.destinationView))
        requireActivity().delayInBackgLaunchInMain(lifecycleScope, 500) {
            processNextMessage()
        }
    }

    override fun onPause() {
        super.onPause()
        activity?.showProgressBar(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.home_mi_refresh -> {
                viewModel.setStateEvent(stateEvent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

    fun addProgressBarObservers() {
        inboxManager.getProgressBarNotifier(stateEvent.destinationView)
            .observe(viewLifecycleOwner, progressBarObserver)

        inboxManager.getMessagesNotifer(stateEvent.destinationView)
            .observe(viewLifecycleOwner, messageObserverForProgressBar)
    }

    fun subscribeNewMessageObserver(){
        messageNotifierLiveData.observe(viewLifecycleOwner, newMessageObserver)
    }

    fun processNextMessage() {
        if (viewModel.inboxManager.getInboxSize(stateEvent.destinationView) == 0) {
            messageNotifierLiveData.observe(viewLifecycleOwner, newMessageObserver)
            return
        }

        if(viewModel.inboxManager.isMessageInInboxInProcess(stateEvent.destinationView)){
            return
        }

        val newMessage = viewModel
            .inboxManager
            .getMessageFromInbox(stateEvent.destinationView) ?: return

        messageNotifierLiveData.removeObserver(newMessageObserver)

        when (newMessage.message.uiType) {
            is UIType.Dialog -> {

                viewModel.inboxManager.setMessageInInboxToProcess(stateEvent.destinationView)
                activity?.showMyDialog(
                    newMessage.message.title,
                    newMessage.message.description
                ) { // when ok button clicked function

                    viewModel.inboxManager.removeMessageFromInbox(stateEvent.destinationView)

                    // wait for a second to proccess next message
                    requireActivity().delayInBackgLaunchInMain(lifecycleScope, 500) {
                        processNextMessage()
                    }
                }
            }

            is UIType.Toast -> {
                activity?.showToast(newMessage.message.description)
                viewModel.inboxManager.removeMessageFromInbox(stateEvent.destinationView)
                requireActivity().delayInBackgLaunchInMain(lifecycleScope, 500) {
                    processNextMessage()
                }
            }
        }

    }

    abstract fun subscribeDataObservers()

    abstract fun initData()

    abstract fun updateView(myModel: T?)

}