package com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel

import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.state.ContactsViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi


@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun ContactViewModel.getCurrentViewStateOrNew(): ContactsViewState
        = viewState.value ?: ContactsViewState()


@ExperimentalCoroutinesApi
@FlowPreview
@InternalCoroutinesApi
fun ContactViewModel.getMessages(): ArrayList<MessageModel>{
    return getCurrentViewStateOrNew().contactMeFragmentView.messages
}

