package com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main

/*
********************** REQUIREMENTS *****************
* 1) when user inserts new skill, it should be inserted
* 2) when user give many skills, they all should be inserted
* 3) all skills should be returned when asked
* 4) all skills should be deleted when requested
* 5) insertManyAndReplace -> when db receives many skills,
* old ones should be deleted and new ones should be inserted
* 6) insertManyAndGet -> when new skills come to db
* old ones should be deleted, new ones inserted and returned
* 7) when skills list inserted and returned make sure it
* hasn't changed after concersition
 */
import androidx.room.*
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.SkillModel


@Dao
interface SkillsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(skillModel: SkillModel)

    @Query("SELECT * FROM skills")
    suspend fun getSkills(): List<SkillModel>

    @Transaction
    open suspend fun insertMany(skillsList: List<SkillModel>) {
        skillsList.forEach { insert(it) }
    }

    @Query("DELETE FROM skills")
    fun deleteAll()

    @Transaction
    open suspend fun insertManyAndReplace(skillsList: List<SkillModel>) {
        deleteAll()
        insertMany(skillsList)
    }

    @Transaction
    open suspend fun insertManyReplaceGet(skillsList: List<SkillModel>): List<SkillModel> {
        insertManyAndReplace(skillsList)
        return getSkills()
    }
}





















