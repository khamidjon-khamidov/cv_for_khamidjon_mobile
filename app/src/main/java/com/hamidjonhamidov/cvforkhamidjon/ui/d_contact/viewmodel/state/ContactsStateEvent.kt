package com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.state

import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.NotificationsModel

sealed class ContactsStateEvent {

    class GetNotifications(): ContactsStateEvent()

    class GetMessages(): ContactsStateEvent()

    class SendMessage(val message: MessageModel): ContactsStateEvent()

    class SaveNotification(val notification: NotificationsModel): ContactsStateEvent()
}
