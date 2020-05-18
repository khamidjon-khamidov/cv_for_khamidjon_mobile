package com.hamidjonhamidov.cvforkhamidjon.util.data_manager

import com.hamidjonhamidov.cvforkhamidjon.util.Message


data class UIMessage(
    val message: Message,
    var progressStatus: Boolean = MESSAGE_IS_NOT_IN_PROCESS
){

    constructor(uiMessage: UIMessage) : this(uiMessage.message, uiMessage.progressStatus)

    companion object{
        val MESSAGE_IN_PROCCESS = true
        val MESSAGE_IS_NOT_IN_PROCESS = false
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UIMessage

        if (message != other.message) return false
        if (progressStatus != other.progressStatus) return false

        return true
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + progressStatus.hashCode()
        return result
    }
}
