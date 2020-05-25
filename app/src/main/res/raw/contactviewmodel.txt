package com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel.Companion.STATUS_NOT_SENT
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel.Companion.WHO_HIM
import com.hamidjonhamidov.cvforkhamidjon.repository.contacs.ContactsRepository
import com.hamidjonhamidov.cvforkhamidjon.repository.contacs.MessageResponse
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.MessageNotifyModel
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.state.ContactsStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.state.ContactsStateEvent.*
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.state.ContactsViewState
import com.hamidjonhamidov.cvforkhamidjon.util.NetworkConnection
import com.hamidjonhamidov.cvforkhamidjon.util.TokenStoreManager
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@InternalCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
class ContactViewModel
@Inject
constructor(
    private val contactsRepository: ContactsRepository,
    private val networkConnection: NetworkConnection,
    private val tokenStoreManager: TokenStoreManager
) : ViewModel() {

    private val TAG = "AppDebug"

    // **************** data channel *******************
    val dataChannel =
        BroadcastChannel<MessageResponse>(1000)

    // **************** viewState **********************
    private val _viewState: MutableLiveData<ContactsViewState> = MutableLiveData()

    val viewState: LiveData<ContactsViewState>
        get() = _viewState

    fun setViewState(newViewState: ContactsViewState) {
        _viewState.value = newViewState
    }

    // ************* message update notifier for a single message *************
    val updateNotifier: MutableLiveData<MessageNotifyModel> = MutableLiveData(
        MessageNotifyModel(-1, -1)
    )

    fun notifyMessageUpdate(position: Int) {
        updateNotifier.value = MessageNotifyModel(position, -1)
    }

    fun notifyMessageInsertion(position: Int) {
        updateNotifier.value = MessageNotifyModel(-1, position)
    }

    init {
        setUpChannel()
    }

    private fun setUpChannel() {
        dataChannel
            .asFlow()
            .onEach { messageResponse ->
                getCurrentViewStateOrNew()
                    .messagesInProcess
                    .remove(messageResponse.order)

                udpateMessageChange(messageResponse)
            }
            .launchIn(viewModelScope)
    }

    private fun offerToDataChannel(messageResponse: MessageResponse) {
        if (!dataChannel.isClosedForSend) {
            dataChannel.offer(messageResponse)
        }
    }

    fun setStateEvent(contactsStateEvent: ContactsStateEvent) {

        when (contactsStateEvent) {

            is GetMessages -> {
                viewModelScope.launch {
                    val messages = contactsRepository.getMessages()
                    withContext(Dispatchers.Main) { setMessages(messages) }
                }
            }

            is SendMessage -> {
                val message = buildMessage(contactsStateEvent.messageStr)
                launchJob(message) {
                    contactsRepository.sendMessage(
                        message,
                        networkConnection.isConectedToInternet(),
                        tokenStoreManager.getToken()?:""
                    )
                }
            }
        }
    }

    private fun buildMessage(mesg: String): MessageModel {
        val lastOrder = tokenStoreManager.getLasOrderNum()

        val msg = MessageModel(
            lastOrder,
            WHO_HIM,
            mesg,
            STATUS_NOT_SENT
        )
        tokenStoreManager.saveLastOrderNumber(lastOrder+1)
        return msg
    }

    private fun launchJob(message: MessageModel, jobFunc: () -> Flow<MessageResponse>) {
        val msgSet = getCurrentViewStateOrNew().messagesInProcess
        if (msgSet.contains(message.order))
            return

        addMessageToList(message)

        jobFunc.invoke()
            .onEach {
                offerToDataChannel(it)
            }
            .launchIn(viewModelScope)
    }

    fun sendUnsentMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            for (i in getMessages().indices) {
                if(viewState.value?.messagesInProcess?.contains(i) == true)
                    continue

                val messsageModel = getMessages()[i]
                if (messsageModel.status == STATUS_NOT_SENT && messsageModel.whoSent== WHO_HIM) {
                    contactsRepository.sendMessage(
                        messsageModel,
                        networkConnection.isConectedToInternet(),
                        tokenStoreManager.getToken()?:""
                    ).onEach {
                        offerToDataChannel(it)
                    }
                        .launchIn(viewModelScope)
                }
            }

        }

    }
}

























