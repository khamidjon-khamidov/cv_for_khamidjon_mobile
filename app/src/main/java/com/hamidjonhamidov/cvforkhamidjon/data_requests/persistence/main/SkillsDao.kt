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
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.Skill


@Dao
interface SkillsDao {

    @Insert
    fun insert(skill: Skill)

    @Query("SELECT * FROM skills")
    fun getSkills(): List<Skill>

    @Transaction
    open fun insertMany(skillsList: List<Skill>) {
        skillsList.forEach { insert(it) }
    }

    @Query("DELETE FROM skills")
    fun deleteAll()

    @Transaction
    open fun insertManyAndReplace(skillsList: List<Skill>){
        deleteAll()
        insertMany(skillsList)
    }

    @Transaction
    open fun insertManyReplaceGet(skillsList: List<Skill>): List<Skill> {
        insertManyAndReplace(skillsList)
        return getSkills()
    }
}





















