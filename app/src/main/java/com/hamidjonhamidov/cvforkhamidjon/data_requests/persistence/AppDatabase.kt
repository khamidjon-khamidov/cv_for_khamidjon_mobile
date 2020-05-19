package com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.*
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.*

@Database(
    entities = [
        AboutMeModel::class,
        SkillModel::class,
        AchievementModel::class,
        ProjectModel::class,
        PostModel::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAboutMeDao(): AboutMeDao

    abstract fun getSkillsDao(): SkillsDao

    abstract fun getAchievementsDao(): AchievementsDao

    abstract fun getProjectsDao(): ProjectsDao

    abstract fun getPostsDao(): PostsDao
}