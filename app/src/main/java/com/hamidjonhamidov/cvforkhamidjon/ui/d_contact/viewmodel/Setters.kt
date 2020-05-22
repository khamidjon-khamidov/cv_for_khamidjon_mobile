package com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel

import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.NotificationsModel
import com.hamidjonhamidov.cvforkhamidjon.repository.contacs.MessageResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi


@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun ContactViewModel.setMessages(messages: ArrayList<MessageModel>) {
    val update = getCurrentViewStateOrNew()
    update.contactMeFragmentView.messages = messages
    setViewState(update)
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun ContactViewModel.setNotifications(notifications: ArrayList<NotificationsModel>) {
    val update = getCurrentViewStateOrNew()
    update.notificationsFragmentView.notifications = notifications
    setViewState(update)
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun ContactViewModel.udpateMessageChange(res: MessageResponse) {
    if (getCurrentViewStateOrNew().contactMeFragmentView.messages == null) return
    if (!res.isSent) return

    for (i in getCurrentViewStateOrNew().contactMeFragmentView.messages!!.indices) {
        val message = getCurrentViewStateOrNew().contactMeFragmentView.messages!![i]
        if (res.order == message.order) {
            message.status = true
            notifiyActiveFragmentWithChanges()
            break
        }
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun ContactViewModel.addMessageToList(message: MessageModel){
    if(getCurrentViewStateOrNew().contactMeFragmentView.messages==null){
        getCurrentViewStateOrNew().contactMeFragmentView.messages = ArrayList()
    }

    getCurrentViewStateOrNew().contactMeFragmentView.messages!!.add(0, message)
    notifiyActiveFragmentWithChanges()
}










