package com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel

import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
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
fun ContactViewModel.udpateMessageChange(res: MessageResponse) {
    if (getMessages().isEmpty()) return
    if (!res.isSent) return

    for (i in getMessages().indices) {
        val message = getMessages()[i]
        if (res.order == message.order) {
            message.status = true
            notifyMessageUpdate(i)
            break
        }
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun ContactViewModel.addMessageToList(message: MessageModel){
    getMessages().add(0, message)
    notifyMessageInsertion(0)
}










