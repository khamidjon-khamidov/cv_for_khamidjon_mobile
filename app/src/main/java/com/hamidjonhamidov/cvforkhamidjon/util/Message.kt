package com.hamidjonhamidov.cvforkhamidjon.util

import com.hamidjonhamidov.cvforkhamidjon.util.UIType.None

data class Message(
    var title: String = "",
    var description: String = "",
    var uiType: UIType = None(),
    var isFromNetwork: Boolean = false
)

sealed class UIType{

    class Toast : UIType()

    class Dialog : UIType()

    class None : UIType()
}