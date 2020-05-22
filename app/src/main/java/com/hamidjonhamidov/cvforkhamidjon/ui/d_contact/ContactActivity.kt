package com.hamidjonhamidov.cvforkhamidjon.ui.d_contact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hamidjonhamidov.cvforkhamidjon.MyApplication
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.di.contacts_subcomponent.ContactsComponent
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@FlowPreview
@OptIn(InternalCoroutinesApi::class)
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class ContactActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val navController: NavController by lazy {
        findNavController(R.id.contact_nav_host_fragment)
    }

    lateinit var contactsComponent: ContactsComponent

    val viewModel: ContactViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        contactsComponent = (application as MyApplication).appComponent
            .contactsComponent()
            .create()
        contactsComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        initViews()
        navigateToNecessaryFragment()
    }

    private fun navigateToNecessaryFragment(){
        val whichFragment = intent.getStringExtra(CONTACT_BUNDLE) ?: TO_NOTIFICATIONS
        if(whichFragment == TO_NOTIFICATIONS){
            navController.navigate(R.id.notificationsFragment)
        } else {
            navController.navigate(R.id.contactMeFragment)
        }
    }

    private fun initViews() {
        // set toolbar
        setSupportActionBar(contact_toolbar)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.contactMeFragment,
                R.id.notificationsFragment
            )
        )

        contact_toolbar
            .setupWithNavController(navController, appBarConfiguration)
    }

    companion object {
        const val CONTACT_BUNDLE = "CONTACT_ACTIVITY"
        const val TO_NOTIFICATIONS = "NOTIFICATIONS_TO"
        const val TO_CONTACTME = "CONTACT_ME_TO"
    }
}


















