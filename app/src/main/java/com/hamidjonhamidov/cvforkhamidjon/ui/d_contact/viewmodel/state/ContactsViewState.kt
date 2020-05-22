package com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.state

import android.os.Parcelable
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.NotificationsModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContactsViewState(
    var messagesInProcess: HashSet<Int> = HashSet(),
    var contactMeFragmentView: ContactMeFragmentView = ContactMeFragmentView(),
    var notificationsFragmentView: NotificationsFragmentView = NotificationsFragmentView()
): Parcelable {

    @Parcelize
    data class ContactMeFragmentView(
        var messages: ArrayList<MessageModel>? = null
    ): Parcelable

    @Parcelize
    data class NotificationsFragmentView(
        var notifications: ArrayList<NotificationsModel>? = null
    ): Parcelable
}