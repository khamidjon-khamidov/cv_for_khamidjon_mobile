package com.hamidjonhamidov.cvforkhamidjon.util

data class DataState<T>(
    val toFragment: String,
    val data: T? = null,
    val message: Message
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DataState<*>

        if (toFragment != other.toFragment) return false
        if (data != other.data) return false
        if (message != other.message) return false

        return true
    }

    override fun hashCode(): Int {
        var result = toFragment.hashCode()
        result = 31 * result + (data?.hashCode() ?: 0)
        result = 31 * result + message.hashCode()
        return result
    }
}