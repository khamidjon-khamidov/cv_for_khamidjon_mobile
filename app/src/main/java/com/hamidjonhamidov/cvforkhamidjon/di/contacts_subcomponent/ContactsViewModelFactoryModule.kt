package com.hamidjonhamidov.cvforkhamidjon.di.contacts_subcomponent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hamidjonhamidov.cvforkhamidjon.di.ViewModelKey
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.ContactViewModel
import com.hamidjonhamidov.cvforkhamidjon.viewmodelfactory.ContactsViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
abstract class ContactsViewModelFactoryModule {

    @FlowPreview
    @Binds
    @IntoMap
    @ViewModelKey(ContactViewModel::class)
    abstract fun bindMainViewModel(viewModel: ContactViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(vmFactoryMain: ContactsViewModelFactory): ViewModelProvider.Factory
}