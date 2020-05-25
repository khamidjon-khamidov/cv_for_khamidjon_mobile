package com.hamidjonhamidov.cvforkhamidjon.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hamidjonhamidov.cvforkhamidjon.util.constants.API_URLS
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// these are used internally
@Module
object InternalVariablesModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson =
        GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()


    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(API_URLS.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))

}