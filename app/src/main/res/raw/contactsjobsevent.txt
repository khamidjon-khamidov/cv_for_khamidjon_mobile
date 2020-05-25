package com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.state

sealed class ContactsJobsEvent {

    class SendMessage(): ContactsJobsEvent()
}