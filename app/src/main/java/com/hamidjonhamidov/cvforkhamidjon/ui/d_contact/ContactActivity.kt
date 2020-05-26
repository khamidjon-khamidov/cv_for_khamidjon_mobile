package com.hamidjonhamidov.cvforkhamidjon.ui.d_contact

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hamidjonhamidov.cvforkhamidjon.MyApplication
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.di.contacts_subcomponent.ContactsComponent
import com.hamidjonhamidov.cvforkhamidjon.services.FMService.Companion.INTENT_ACTION_SEND_MESSAGE
import com.hamidjonhamidov.cvforkhamidjon.services.FMService.Companion.INTENT_KEY_NAME
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.ContactViewModel
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.state.ContactsStateEvent.GetMessages
import com.hamidjonhamidov.cvforkhamidjon.util.constants.PERSONAL_INFO.PHONE_NUM
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

    companion object {
        var isActive = false
    }

    private val TAG = "AppDebug"

    private val receiver: BroadcastReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val num = p1?.getBooleanExtra(INTENT_KEY_NAME, false) ?: false
                if (num) {
                    viewModel.setStateEvent(GetMessages())
                }
            }
        }
    }

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

        setSupportActionBar(contact_toolbar)

        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.contact_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){

            R.id.contact_mi_call -> {
                val myIntent = Intent(Intent.ACTION_DIAL)
                val uri = "tel:$PHONE_NUM"
                myIntent.data = Uri.parse(uri)
                startActivity(myIntent)
                return true
            }

            R.id.contact_mi_message -> {
                val uri =
                    Uri.parse(String.format("smsto:%s", PHONE_NUM))
                val smsIntent = Intent(Intent.ACTION_SENDTO, uri)
                startActivityForResult(Intent.createChooser(smsIntent, "Choose SMS App"), 12);
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(INTENT_ACTION_SEND_MESSAGE)
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter)
        isActive = true
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
        isActive = false
    }

    private fun initViews() {
        // set toolbar
        setSupportActionBar(contact_toolbar)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.contactMeFragment
            )
        )

        contact_toolbar
            .setupWithNavController(navController, appBarConfiguration)
    }
}


















