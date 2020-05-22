package com.hamidjonhamidov.cvforkhamidjon.fragment_builders.contact

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.ContactActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ContactsNavHostFragment : NavHostFragment(){

    private val TAG = "AppDebug"

    @Inject
    lateinit var contacsFragmentFactory: FragmentFactory


    override fun onAttach(context: Context) {
        (activity as ContactActivity)
            .contactsComponent
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = contacsFragmentFactory
        super.onCreate(savedInstanceState)
    }
}




















