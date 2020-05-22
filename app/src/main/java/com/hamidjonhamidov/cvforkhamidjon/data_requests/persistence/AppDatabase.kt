package com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.achievments.AchievementsDao
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.contact.ContactsDao
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.*
import com.hamidjonhamidov.cvforkhamidjon.models.offline.achievements.AchievementModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.NotificationsModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.*

@Database(
    entities = [
        AboutMeModel::class,
        SkillModel::class,
        AchievementModel::class,
        ProjectModel::class,
        PostModel::class,
        NotificationsModel::class,
        MessageModel::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getContacsDao(): ContactsDao

    abstract fun getAboutMeDao(): AboutMeDao

    abstract fun getSkillsDao(): SkillsDao

    abstract fun getAchievementsDao(): AchievementsDao

    abstract fun getProjectsDao(): ProjectsDao

    abstract fun getPostsDao(): PostsDao
}