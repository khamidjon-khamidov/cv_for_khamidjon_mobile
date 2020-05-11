package com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence

import org.junit.Test

import org.junit.Assert.*

class ConvertersTest {

    @Test
    fun fromListStringToString() {
        // Arrange
        val list = listOf("one", "two", "three", "four")

        // Act
        val str = Converters().fromListStringToString(list)

        // Assert
        assertEquals(str, "one, two, three, four")
    }

    @Test
    fun fromStringToListString() {
        // Arrange
        val str = "one, two, three, four"

        // Act
        val list = Converters().fromStringToListString(str)

        // Assert
        assertEquals(list, listOf("one", "two", "three", "four"))
    }
}