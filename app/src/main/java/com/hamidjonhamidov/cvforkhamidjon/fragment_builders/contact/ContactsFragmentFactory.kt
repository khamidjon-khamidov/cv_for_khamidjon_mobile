package com.hamidjonhamidov.cvforkhamidjon.fragment_builders.contact

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.hamidjonhamidov.cvforkhamidjon.di.contacts_subcomponent.ContactsScope
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.a_contact_me.ContactMeFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.b_notifications.NotificationsFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@InternalCoroutinesApi
@ContactsScope
class ContactsFragmentFactory
@Inject
constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory
) : FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String) :Fragment =

        when(className){
            ContactMeFragment::class.java.name -> {
                ContactMeFragment(viewModelProviderFactory)
            }

            NotificationsFragment::class.java.name -> {
                NotificationsFragment(viewModelProviderFactory)
            }

            else -> super.instantiate(classLoader, className)
        }
}



















