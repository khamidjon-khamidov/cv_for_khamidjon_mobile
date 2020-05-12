package com.hamidjonhamidov.cvforkhamidjon.util

data class DataState<T>(
    val toFragment: String,
    val data: T?,
    val message: Message?
)