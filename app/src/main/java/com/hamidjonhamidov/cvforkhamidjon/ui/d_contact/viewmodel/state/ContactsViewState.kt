package com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.state

import android.os.Parcelable
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContactsViewState(
    var messagesInProcess: HashSet<Int> = HashSet(),
    var contactMeFragmentView: ContactMeFragmentView = ContactMeFragmentView()
): Parcelable {

    @Parcelize
    data class ContactMeFragmentView(
        var messages: ArrayList<MessageModel> = ArrayList()
    ): Parcelable
}