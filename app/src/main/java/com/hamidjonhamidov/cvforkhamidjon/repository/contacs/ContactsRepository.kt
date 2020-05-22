package com.hamidjonhamidov.cvforkhamidjon.repository.contacs

import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.NotificationsModel
import kotlinx.coroutines.flow.Flow

interface ContactsRepository  {

    fun sendMessage(
        message: MessageModel,
        isNetworkAvailable: Boolean
    )
    : Flow<MessageResponse>

    suspend fun getMessages(): ArrayList<MessageModel>

    suspend fun getNotificatins(): ArrayList<NotificationsModel>

    suspend fun saveNotification(notificationsModel: NotificationsModel)
}