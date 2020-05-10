package com.hamidjonhamidov.cvforkhamidjon.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ViewModeKey(val value: KClass<out ViewModel>)