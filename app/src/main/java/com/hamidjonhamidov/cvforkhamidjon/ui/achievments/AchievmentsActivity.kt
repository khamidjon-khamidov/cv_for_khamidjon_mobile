package com.hamidjonhamidov.cvforkhamidjon.ui.achievments

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hamidjonhamidov.cvforkhamidjon.MyApplication
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.di.achievements_subcomponent.AchievementsComponent
import com.hamidjonhamidov.cvforkhamidjon.ui.achievments.viewmodel.AchievementsViewModel
import com.hamidjonhamidov.cvforkhamidjon.ui.achievments.viewmodel.state.AchievementsViewDestEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.delayInBackgLaunchInMain
import com.hamidjonhamidov.cvforkhamidjon.ui.showMyDialog
import com.hamidjonhamidov.cvforkhamidjon.ui.showProgressBar
import com.hamidjonhamidov.cvforkhamidjon.ui.showToast
import com.hamidjonhamidov.cvforkhamidjon.util.UIType
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_ALREADY_IN_PROGRESS
import com.hamidjonhamidov.cvforkhamidjon.util.data_manager.InboxManager
import com.hamidjonhamidov.cvforkhamidjon.util.data_manager.UIMessage
import com.hamidjonhamidov.cvforkhamidjon.util.setupWithMyNavController
import kotlinx.android.synthetic.main.activity_achievments.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class AchievmentsActivity : BaseActivity() {

    private val TAG = "AppDebug"

    lateinit var achievementsComponent: AchievementsComponent

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    val viewModel: AchievementsViewModel by viewModels {
        viewModelFactory
    }

    val progressBarObserver = Observer<Boolean> {
        showProgressBar(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        achievementsComponent = (application as MyApplication)
            .appComponent
            .achievmentsComponent()
            .create()
        achievementsComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievments)


        if (savedInstanceState == null) {
            setupBottomNavigationBar()

            setSupportActionBar(achievments_toolbar)
        }

        shouldRefresh()
        addProgressBarObservers()
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState!!)
        setupBottomNavigationBar()
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

    val inboxManager: InboxManager<AchievementsViewDestEvent> by lazy {
        viewModel.inboxManager
    }

    override fun onResume() {
        super.onResume()
        showProgressBar(inboxManager.getProgressBarState(stateEvent.destinationView))
        delayInBackgLaunchInMain(lifecycleScope, 500) {
            processNextMessage()
        }

        subscribeNewMessageObserver()
    }

    override fun onStop() {
        super.onStop()
        unsubscribeNewMessageObserver()
    }

    fun shouldRefresh() {
        if (viewModel.viewState.value?.achievementsFragmentView?.achievements == null) {
            viewModel.setStateEvent(stateEvent)
        }
    }

    fun addProgressBarObservers() {
        inboxManager.getProgressBarNotifier(stateEvent.destinationView)
            .observe(this, progressBarObserver)

        inboxManager.getMessagesNotifer(stateEvent.destinationView)
            .observe(this, messageObserverForProgressBar)
    }

    fun subscribeNewMessageObserver() {
        messageNotifierLiveData.observe(this, newMessageObserver)
    }

    fun unsubscribeNewMessageObserver() {
        messageNotifierLiveData.removeObserver(newMessageObserver)
    }

    fun processNextMessage() {
        if (viewModel.inboxManager.getInboxSize(stateEvent.destinationView) == 0) {
            messageNotifierLiveData.observe(this, newMessageObserver)
            return
        }

        if (viewModel.inboxManager.isMessageInInboxInProcess(stateEvent.destinationView)) {
            return
        }

        val newMessage = viewModel
            .inboxManager
            .getMessageFromInbox(stateEvent.destinationView) ?: return

        messageNotifierLiveData.removeObserver(newMessageObserver)

        when (newMessage.message.uiType) {
            is UIType.Dialog -> {

                viewModel.inboxManager.setMessageInInboxToProcess(stateEvent.destinationView)
                showMyDialog(
                    newMessage.message.title,
                    newMessage.message.description
                ) { // when ok button clicked function

                    viewModel.inboxManager.removeMessageFromInbox(stateEvent.destinationView)

                    // wait for a second to proccess next message
                    delayInBackgLaunchInMain(lifecycleScope, 500) {
                        processNextMessage()
                    }
                }
            }

            is UIType.Toast -> {
                showToast(newMessage.message.description)
                viewModel.inboxManager.removeMessageFromInbox(stateEvent.destinationView)
                delayInBackgLaunchInMain(lifecycleScope, 500) {
                    processNextMessage()
                }
            }
        }
    }


    fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navGraphIds = listOf(
            R.navigation.android_nav_graph,
            R.navigation.algorithms_nav_graph,
            R.navigation.others_nav_graph
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithMyNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent,
            fragmentFactory = fragmentFactory
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}



















