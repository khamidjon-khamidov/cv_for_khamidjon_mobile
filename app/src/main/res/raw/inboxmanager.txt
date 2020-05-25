package com.hamidjonhamidov.cvforkhamidjon.util.data_manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hamidjonhamidov.cvforkhamidjon.util.Message
import java.util.*
import kotlin.collections.HashMap
import kotlin.reflect.KClass

class InboxManager<DestinationUI : Any> {

    private val TAG = "AppDebug"

    private val uiNewMessageNotifier = HashMap<KClass<out DestinationUI>, MutableLiveData<UIMessage>>()
    private val messages: HashMap<KClass<out DestinationUI>, Queue<UIMessage>> = HashMap()
    private val progressBarState: HashMap<KClass<out DestinationUI>, MutableLiveData<Boolean>> =
        HashMap()


    // *************** Progress bar ****************

    fun getProgressBarState(dest: DestinationUI): Boolean {
        return progressBarState[dest::class]?.value ?: false
    }

    fun setProgressBarStateAndNotify(dest: DestinationUI, state: Boolean) {
        val notifier = getCurrentProgressBarNotifierOrNew(dest)
        notifier.value = state
    }

    fun getProgressBarNotifier(dest: DestinationUI): LiveData<Boolean> {
        return getCurrentProgressBarNotifierOrNew(dest)
    }

    private fun getCurrentProgressBarNotifierOrNew(dest: DestinationUI): MutableLiveData<Boolean>{
        if (progressBarState[dest::class] == null) {
            progressBarState[dest::class] = MutableLiveData()
        }

        return progressBarState[dest::class]!!
    }

    // *************** Messages *******************
    fun notifyWithNewMessage(dest: DestinationUI, message: Message) {
        val notifier = getCurrentMessageNotifierOrNew(dest)
        notifier.value = UIMessage(message)
    }

    fun getMessagesNotifer(dest: DestinationUI): LiveData<UIMessage> {
        return getCurrentMessageNotifierOrNew(dest)
    }

    fun receiveNewMessage(dest: DestinationUI, message: Message) {
        addMessageToInbox(dest, message)

        notifyWithNewMessage(dest, message)
    }

    private fun getCurrentMessageNotifierOrNew(dest: DestinationUI): MutableLiveData<UIMessage>{
        if(uiNewMessageNotifier[dest::class]==null){
            uiNewMessageNotifier[dest::class] = MutableLiveData()
        }

        return uiNewMessageNotifier[dest::class]!!
    }

    fun getMessageFromInbox(dest: DestinationUI): UIMessage? {
        return messages[dest::class]?.peek()
    }

    fun isMessageInInboxInProcess(dest: DestinationUI): Boolean {
        return messages[dest::class]?.peek()?.progressStatus == UIMessage.MESSAGE_IN_PROCCESS
    }

    fun getInboxSize(dest: DestinationUI): Int {
        return messages[dest::class]?.size ?: 0
    }

    fun setMessageInInboxToProcess(dest: DestinationUI) {
        messages[dest::class]?.peek()?.progressStatus = UIMessage.MESSAGE_IN_PROCCESS
    }

    fun removeMessageFromInbox(dest: DestinationUI) {
        messages[dest::class]?.poll()
    }

    private fun addMessageToInbox(dest: DestinationUI, message: Message) {
        var queue: Queue<UIMessage>? = messages[dest::class]
        if (queue == null) {
            queue = LinkedList()
        }

        queue.add(UIMessage(message))
        messages[dest::class] = queue
    }

    fun clearMessages(destinationUI: DestinationUI) {
        messages[destinationUI::class]?.clear()
    }
}
















