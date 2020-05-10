package com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.AboutMeDao
import com.hamidjonhamidov.cvforkhamidjon.models.offline.AboutMeInfo

@Database(
    entities = [
        AboutMeInfo::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getAboutMeDao(): AboutMeDao
}