package com.hamidjonhamidov.cvforkhamidjon.util.data_manager

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hamidjonhamidov.cvforkhamidjon.util.Message
import java.util.*
import kotlin.collections.HashMap
import kotlin.reflect.KClass

class InboxManager<DestinationUI : Any> {

    private val TAG = "AppDebug"

    private val uiNewMessageNotifier = HashMap<String, MutableLiveData<UIMessage>>()
    private val messages: HashMap<String, Queue<UIMessage>> = HashMap()
    private val progressBarState: HashMap<String, MutableLiveData<BoolWrapper>> = HashMap()

    // *************** Progress bar ****************

    fun getProgressBarState(dest: DestinationUI): Boolean {
        return progressBarState[dest::class.simpleName]?.value?.state ?: false
    }

    fun setProgressBarStateAndNotify(dest: DestinationUI, state: Boolean) {
        val notifier = getCurrentProgressBarNotifierOrNew(dest)
        notifier.value = BoolWrapper(state)
    }

    fun getProgressBarNotifier(dest: DestinationUI): LiveData<BoolWrapper> {
        Log.d(TAG, "getProgressBarNotifier: ${getCurrentProgressBarNotifierOrNew(dest)}")
        return getCurrentProgressBarNotifierOrNew(dest)
    }

    private fun getCurrentProgressBarNotifierOrNew(dest: DestinationUI): MutableLiveData<BoolWrapper>{
        if (progressBarState[dest::class.simpleName] == null) {
            progressBarState[dest::class.simpleName!!] = MutableLiveData(BoolWrapper(false))
        }

        return progressBarState[dest::class.simpleName]!!
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
        if(uiNewMessageNotifier[dest::class.simpleName]==null){
            uiNewMessageNotifier[dest::class.simpleName!!] = MutableLiveData()
        }

        return uiNewMessageNotifier[dest::class.simpleName]!!
    }

    fun getMessageFromInbox(dest: DestinationUI): UIMessage? {
        return messages[dest::class.simpleName]?.peek()
    }

    fun isMessageInInboxInProcess(dest: DestinationUI): Boolean {
        return messages[dest::class.simpleName]?.peek()?.progressStatus == UIMessage.MESSAGE_IN_PROCCESS
    }

    fun getInboxSize(dest: DestinationUI): Int {
        return messages[dest::class.simpleName]?.size ?: 0
    }

    fun setMessageInInboxToProcess(dest: DestinationUI) {
        messages[dest::class.simpleName]?.peek()?.progressStatus = UIMessage.MESSAGE_IN_PROCCESS
    }

    fun removeMessageFromInbox(dest: DestinationUI) {
        messages[dest::class.simpleName]?.poll()
    }

    private fun addMessageToInbox(dest: DestinationUI, message: Message) {
        var queue: Queue<UIMessage>? = messages[dest::class.simpleName]
        if (queue == null) {
            queue = LinkedList()
        }

        queue.add(UIMessage(message))
        messages[dest::class.simpleName!!] = queue
    }

    fun clearMessages(destinationUI: DestinationUI) {
        messages[destinationUI::class.simpleName]?.clear()
    }
}

data class BoolWrapper(
    val state: Boolean
)














