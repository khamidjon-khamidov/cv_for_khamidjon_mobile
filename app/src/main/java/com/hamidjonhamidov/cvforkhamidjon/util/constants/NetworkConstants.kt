package com.hamidjonhamidov.cvforkhamidjon.util.constants

import com.hamidjonhamidov.cvforkhamidjon.util.Message
import com.hamidjonhamidov.cvforkhamidjon.util.UIType

object NetworkConstants {

    const val NETWORK_TIMEOUT = 5000L
    var NETWORK_DELAY = 0L

    // network errors
    const val NETWORK_ERROR_TIMEOUT = "Network Timeout"
    val NETWORK_ERROR_FAILURE = "Network Failure"
    const val NETWORK_SUCCESS = "Network Success"
    const val NETWORK_ERROR_NO_INTERNET = "No Internet"

    // network timeout cache success
    const val NETWORK_TIMEOUT_CACHE_SUCCESS_TITLE = "Cache Success! Network Timeout!"
    const val NETWORK_TIMEOUT_CACHE_SUCCESS_DES = "Data received from Cache due to Slow Network."

    // network timeout cache failure
    const val NETWORK_TIMEOUT_CACHE_EMPTY_TITLE = "Slow Network! Cache Empty!"
    const val NETWORK_TIMEOUT_CACHE_EMPTY_DES = "Sorry, Network Slow and Cache Empty! Data Unavailable! Please try again!"

    // network success, cached offline
    const val NETWORK_CACHE_SUCCESS_TITLE = "Success"
    const val NETWORK_CACHE_SUCCESS_DES = "Data successfully received from Server and saved to Cache."

    // no internet connection, cache success
    const val NO_INTERNET_CACHE_SUCCESS_TITLE = "Cache Success! No Connection!"
    const val NO_INTERNET_CACHE_SUCCESS_DES = "Data received from Cache due to Network Connection."

    // network error, cache success
    const val NETWORK_ERROR_CACHE_SUCCESS_TITLE = "Cache Success! Network Failure!"
    const val NETWORK_ERROR_CACHE_SUCCESS_DES = "Data received from Cache due to Network Error."

    // no internet connection, cache empty
    const val NO_INTERNET_CACHE_EMPTY_TITLE = "No Connection! Cache Empty!"
    const val NO_INTERNET_CACHE_EMPTY_DES = "Sorry, No Connection and Cache Empty! Data Unavailable! Please, CHECK your CONNECTION try again!"

    // network error, cache empty
    const val NETWORK_ERROR_CACHE_EMPTY_TITLE = "Network Failure! Cache Empty!"
    const val NETWORK_ERROR_CACHE_EMPTY_DES = "Sorry, due to network failure and empty cache, NO DATA available! Please try again!"

    // network not allowed, cache success
    val NETWORK_NOT_ALLOWED_CACHE_SUCCESS_TITLE = "Success"
    val NETWORK_NOT_ALLOWED_CACHE_SUCCESS_DES = "As your daily refresh limit finished, Data have been received from Database!"

    // network not allowed, cache empty
    val NETWORK_NOT_ALLOWED_CACHE_EMPTY_TITLE = "Failure"
    val NETWORK_NOT_ALLOWED_CACHE_EMPTY_DES = "For some reason data unavailable despite you run of refresh limits. Please try tomorrow!"



    // possible messages
    // network success
    val MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS = Message(
        NETWORK_CACHE_SUCCESS_TITLE,
        NETWORK_CACHE_SUCCESS_DES,
        UIType.Toast(),
        true
    )

    // no internet connection, cache success
    val MESSAGE_NO_INTERNET_CACHE_SUCCESS = Message(
        NO_INTERNET_CACHE_SUCCESS_TITLE,
        NO_INTERNET_CACHE_SUCCESS_DES,
        UIType.Toast(),
        false
    )

    // network error, cache success
    val MESSAGE_NETWORK_ERROR_CACHE_SUCCESS = Message(
        NETWORK_ERROR_CACHE_SUCCESS_TITLE,
        NETWORK_ERROR_CACHE_SUCCESS_DES,
        UIType.Toast(),
        false
    )

    // no internet connection, cache empty
    val MESSAGE_NO_INTERNET_CACHE_EMPTY = Message(
        NO_INTERNET_CACHE_EMPTY_TITLE,
        NO_INTERNET_CACHE_EMPTY_DES,
        UIType.Dialog(),
        false
    )

    // network error, cache empty
    val MESSAGE_NETWORK_ERROR_CACHE_EMPTY = Message(
        NETWORK_ERROR_CACHE_EMPTY_TITLE,
        NETWORK_ERROR_CACHE_EMPTY_DES,
        UIType.Dialog(),
        false
    )

    // network timeout, cache success
    val MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS = Message(
        NETWORK_TIMEOUT_CACHE_SUCCESS_TITLE,
        NETWORK_TIMEOUT_CACHE_SUCCESS_DES,
        UIType.Toast(),
        false
    )

    // network timeout, cache empty
    val MESSAGE_NETWORK_TIMEOUT_CACHE_EMPTY = Message(
        NETWORK_TIMEOUT_CACHE_EMPTY_TITLE,
        NETWORK_TIMEOUT_CACHE_EMPTY_DES,
        UIType.Dialog(),
        false
    )

    // network not allowed, cache success
    val MESSAGE_NETWORK_NOT_ALLOWED_CACHE_SUCCESS = Message(
        NETWORK_NOT_ALLOWED_CACHE_SUCCESS_TITLE,
        NETWORK_NOT_ALLOWED_CACHE_SUCCESS_DES,
        UIType.Toast(),
        false
    )

    // network not allowed, cache empty
    val MESSAGE_NETWORK_NOT_ALLOWED_CACHE_EMPTY = Message(
        NETWORK_NOT_ALLOWED_CACHE_EMPTY_TITLE,
        NETWORK_NOT_ALLOWED_CACHE_EMPTY_DES,
        UIType.Dialog(),
        false
    )

    // if refresh limit finished
    val MESSAGE_NOT_ALLOWED =
        Message(
            "Warning!!!",
            "As you daily limits has finished, data will be provided from Database",
            UIType.Toast(),
            false
        )

    // message to inform user about butten has already pressed
    val MESSAGE_ALREADY_IN_PROGRESS =
        Message(
            "",
            "Sorry, It is already in progress!",
            UIType.Toast(),
            false
        )

}














