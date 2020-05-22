package com.hamidjonhamidov.cvforkhamidjon.di.contacts_subcomponent

import com.google.gson.Gson
import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.contacts.ContactsApiService
import com.hamidjonhamidov.cvforkhamidjon.util.constants.SOME_CONSTANTS.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object ContactsModule {

    @JvmStatic
    @ContactsScope
    @Provides
    fun provideApiService(gson: Gson): ContactsApiService =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ContactsApiService::class.java)
}