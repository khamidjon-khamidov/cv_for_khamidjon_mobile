package com.hamidjonhamidov.cvforkhamidjon.di.contacts_subcomponent

import com.hamidjonhamidov.cvforkhamidjon.repository.achievments.AchievementsRepository
import com.hamidjonhamidov.cvforkhamidjon.repository.achievments.AchievementsRepositoryImpl
import com.hamidjonhamidov.cvforkhamidjon.repository.contacs.ContactsRepository
import com.hamidjonhamidov.cvforkhamidjon.repository.contacs.ContactsRepositoryImpl
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
abstract class ContactsModuleAbstract {

    @Binds
    abstract fun provideContactsRepository(
        repository: ContactsRepositoryImpl
    )
            : ContactsRepository
}