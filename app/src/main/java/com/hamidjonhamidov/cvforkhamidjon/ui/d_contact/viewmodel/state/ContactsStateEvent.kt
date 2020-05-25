package com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.state

import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel

sealed class ContactsStateEvent {

    class GetMessages(): ContactsStateEvent()

    class SendMessage(val messageStr: String): ContactsStateEvent()
}
