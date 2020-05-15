package com.hamidjonhamidov.cvforkhamidjon.ui.main.a_home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider

import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.ui.MainUiCommunicationListener
import com.hamidjonhamidov.cvforkhamidjon.ui.main.BaseMainFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.MainViewModel
import com.hamidjonhamidov.cvforkhamidjon.ui.showDialog
import com.hamidjonhamidov.cvforkhamidjon.ui.showToast
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class HomeFragment
constructor(
        private val viewModelFactory: ViewModelProvider.Factory,
        private val requestManager: GlideManager
) : BaseMainFragment(R.layout.fragment_home, viewModelFactory) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home_mi_download -> {
                // todo
                return true
            }

            R.id.home_mi_refresh -> {
                // todo
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }
}
