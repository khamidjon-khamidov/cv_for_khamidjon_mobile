package com.hamidjonhamidov.cvforkhamidjon.di.contacts_subcomponent

import com.hamidjonhamidov.cvforkhamidjon.fragment_builders.contact.ContactsNavHostFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.ContactActivity
import dagger.Subcomponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@ContactsScope
@Subcomponent(
    modules = [
        ContactsFragmentModule::class,
        ContactsModuleAbstract::class,
        ContactsModule::class,
        ContactsViewModelFactoryModule::class
    ]
)
interface ContactsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ContactsComponent
    }

    // subcomponent to be injected from here
    fun inject(contactActivity: ContactActivity)

    fun inject(navhostFragment: ContactsNavHostFragment)
}