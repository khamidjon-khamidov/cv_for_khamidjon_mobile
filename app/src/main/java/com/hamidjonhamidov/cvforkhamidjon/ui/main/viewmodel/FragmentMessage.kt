package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel

import com.hamidjonhamidov.cvforkhamidjon.util.Message


data class FragmentMessage(
    val message: Message,
    var progressStatus: Boolean = MESSAGE_IS_NOT_IN_PROGRESS
){
    companion object{
        val MESSAGE_IN_PROGRESS = true
        val MESSAGE_IS_NOT_IN_PROGRESS = false
    }
}
