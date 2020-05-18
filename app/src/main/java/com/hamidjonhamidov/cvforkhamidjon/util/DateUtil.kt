package com.hamidjonhamidov.cvforkhamidjon.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil{
    private val dateFormat = "dd/MM/yyyy hh:mm:ss"
    private const val PROGRAMMING_EXPERIENCE_DATE = "05/09/2017 08:00:00.000"
    private val formatter = SimpleDateFormat(dateFormat, Locale.ENGLISH)

    fun getDifferenceWithCurrentDate(): String {
        val oldMSeconds = getMillisecondsFromDate(PROGRAMMING_EXPERIENCE_DATE)
        val currentMSeconds = System.currentTimeMillis()

        val difSeconds = (currentMSeconds - oldMSeconds) / 1000

        val years = (difSeconds / 3600 / 24 / 365).toInt()
        val months = ((difSeconds - years * 3600 * 24 * 365) / 3600 / 24 / 30).toInt()
        val days =
            ((difSeconds - years * 3600 * 24 * 365 - months * 30 * 24 * 3600) / 3600 / 24).toInt()

        return "$years years $months month(s) $days day(s) "
    }

    private fun getMillisecondsFromDate(date: String) = formatter.parse(date).time
}