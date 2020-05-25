package com.hamidjonhamidov.cvforkhamidjon.repository.contacs

import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.contacts.ContactsApiService
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.di.contacts_subcomponent.ContactsScope
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel.Companion.STATUS_SENT
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel.Companion.WHO_ME
import com.hamidjonhamidov.cvforkhamidjon.util.TokenStoreManager
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.NETWORK_DELAY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.NETWORK_TIMEOUT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.Response
import javax.inject.Inject

@ContactsScope
class ContactsRepositoryImpl
@Inject
constructor(
    val apiService: ContactsApiService,
    val appDatabase: AppDatabase,
    private val tokenStoreManager: TokenStoreManager
) : ContactsRepository {

    private val TAG = "AppDebug"

    override fun sendMessage(
        message: MessageModel,
        isNetworkAvailable: Boolean,
        token: String
    ): Flow<MessageResponse> = flow {
        // if message is already send or the message is to himself from me then return message not sent
        if (message.status == STATUS_SENT && message.whoSent == WHO_ME) {
            emit(MessageResponse(message.order, false))
        }
        // otherwise try to send messages
        else {
            // save message to memory in the first pace
            appDatabase.getContacsDao().insertMesssage(message)

            // send message and return updated message if sent otherwise return itself
            val response = withContext(Dispatchers.IO) {
                try {
                    var remoteResponse: Response<Any>
                    withTimeout(NETWORK_TIMEOUT) {
                        delay(NETWORK_DELAY)

                        if (tokenStoreManager.isRestricted()) {
                            message.copy(status = STATUS_SENT)
                        } else {
                            remoteResponse =
                                apiService.sendMessage("Msg: ${message.msg}\n\nToken: $token")
                            if (remoteResponse.isSuccessful) {
                                message.copy(status = STATUS_SENT)
                            } else {
                                message.copy()
                            }
                        }

                    }
                } catch (throwable: Throwable) {
                    message.copy()
                }
            }

            // if message has been sent, return success
            if (response.status == STATUS_SENT) {
                appDatabase.getContacsDao().updateMessage(response)
                emit(MessageResponse(response.order, true))
            } else {
                emit(MessageResponse(response.order, false))
            }
        }
    }

    override suspend fun getMessages(): ArrayList<MessageModel> {
        val list = appDatabase.getContacsDao().getAllMessages()
        val mList = ArrayList<MessageModel>()
        mList.addAll(list)
        return mList
    }
}















