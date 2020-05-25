package com.hamidjonhamidov.cvforkhamidjon.util

import com.hamidjonhamidov.cvforkhamidjon.util.UIType.None

data class Message(
    var title: String = "",
    var description: String = "",
    var uiType: UIType = None(),
    var isFromNetwork: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (title != other.title) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}

sealed class UIType{

    class Toast : UIType()

    class Dialog : UIType()

    class None : UIType()
}