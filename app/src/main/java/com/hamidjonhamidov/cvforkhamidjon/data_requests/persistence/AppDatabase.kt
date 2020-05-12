package com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.AboutMeDao
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.AchievementsDao
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.ProjectsDao
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.SkillsDao
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AchievementModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.ProjectModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.SkillModel

@Database(
    entities = [
        AboutMeModel::class,
        SkillModel::class,
        AchievementModel::class,
        ProjectModel::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAboutMeDao(): AboutMeDao

    abstract fun getSkillsDao(): SkillsDao

    abstract fun getAchievementsDao(): AchievementsDao

    abstract fun getProjectsDao(): ProjectsDao
}