package com.hamidjonhamidov.cvforkhamidjon.di.contacts_subcomponent

import androidx.fragment.app.FragmentFactory
import com.hamidjonhamidov.cvforkhamidjon.fragment_builders.contact.ContactsFragmentFactory
import com.hamidjonhamidov.cvforkhamidjon.viewmodelfactory.ContactsViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
object ContactsFragmentModule {

    @JvmStatic
    @ContactsScope
    @Provides
    fun provideContactFragmentFactory(
        contactsViewModelFactory: ContactsViewModelFactory
    )
    : FragmentFactory =
        ContactsFragmentFactory(contactsViewModelFactory)
}