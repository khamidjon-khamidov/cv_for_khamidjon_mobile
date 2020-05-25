package com.hamidjonhamidov.cvforkhamidjon.util

data class DataState<ViewState, StateEvent>(
    val stateEvent: StateEvent,
    val viewState: ViewState? = null,
    val message: Message
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DataState<*, *>

        if (stateEvent != other.stateEvent) return false
        if (viewState != other.viewState) return false
        if (message != other.message) return false

        return true
    }

    override fun hashCode(): Int {
        var result = stateEvent?.hashCode() ?: 0
        result = 31 * result + (viewState?.hashCode() ?: 0)
        result = 31 * result + message.hashCode()
        return result
    }
}