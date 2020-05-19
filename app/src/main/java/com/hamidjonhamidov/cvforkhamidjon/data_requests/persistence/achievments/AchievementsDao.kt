package com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.achievments

import androidx.room.*
import com.hamidjonhamidov.cvforkhamidjon.models.offline.achievements.AchievementModel

/*
********************** REQUIREMENTS *****************
* 0) when required, db should return all achivements
* 1) when new achievement comes to db, it should be inserted
* 2) when many achievements comes to db, they all should be inserted
* 3) all achievements should be returned when asked
* 4) all achivements should be deleted when requested
* 5) insertManyAndReplace -> when user gives many achievments,
* old ones should be deleted and new ones should be inserted
* 6) insertManyAndGet -> when new achievements come to db
* old ones should be deleted, new ones inserted and returned
 */

@Dao
interface AchievementsDao {

    @Query("SELECT * FROM achievements")
    suspend fun getAllAchievements(): List<AchievementModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(achievement: AchievementModel)

    @Transaction
    open suspend fun insertMany(achivementsList: List<AchievementModel>) {
        achivementsList.forEach { insert(it) }
    }

    @Query("DELETE FROM achievements")
    suspend fun deleteAll()

    @Transaction
    open suspend fun insertManyAndReplace(achievements: List<AchievementModel>) {
        deleteAll()
        insertMany(achievements)
    }

    @Transaction
    open suspend fun insertManyAndGet(achievements: List<AchievementModel>): List<AchievementModel> {
        insertManyAndReplace(achievements)
        return getAllAchievements()
    }
}
























