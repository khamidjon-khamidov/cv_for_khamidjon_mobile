package com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.state

sealed class ContactsViewDestEvent {

    class NotificationsFragmentDest: ContactsViewDestEvent()

    class ContactMeFragmentDest: ContactsViewDestEvent()
}