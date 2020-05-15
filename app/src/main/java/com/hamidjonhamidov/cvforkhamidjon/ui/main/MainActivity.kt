package com.hamidjonhamidov.cvforkhamidjon.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.hamidjonhamidov.cvforkhamidjon.MyApplication
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.di.main_subcomponent.MainComponent
import com.hamidjonhamidov.cvforkhamidjon.ui.MainUiCommunicationListener
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MainActivity : AppCompatActivity(), MainUiCommunicationListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var mainComponent: MainComponent

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment)
    }


    val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (application as MyApplication).appComponent
            .mainComponent()
            .create()
        mainComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appBarConfiguration =
            AppBarConfiguration(setOf(
                R.id.homeFragment,
                R.id.aboutMeFragment,
                R.id.achievementsFragment,
                R.id.aboutAppFragment,
                R.id.notificationsFragment
            ), drawerLayout = drawer_layout)

        findViewById<Toolbar>(R.id.main_toolbar)
            .setupWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

}


















