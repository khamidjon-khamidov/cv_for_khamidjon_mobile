package com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.AboutMeDao
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.SkillsDao
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeInfo
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.Skill

@Database(
    entities = [
        AboutMeInfo::class,
        Skill::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getAboutMeDao(): AboutMeDao

    abstract fun getSkillsDao(): SkillsDao
}