package com.hamidjonhamidov.cvforkhamidjon.repository.contacs

import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import kotlinx.coroutines.flow.Flow

interface ContactsRepository  {

    fun sendMessage(
        message: MessageModel,
        isNetworkAvailable: Boolean,
        token: String
    )
    : Flow<MessageResponse>

    suspend fun getMessages(): ArrayList<MessageModel>
}