package com.hamidjonhamidov.cvforkhamidjon.instrumentation.data_requests_t.persistence_t.main_t

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.SkillsDao
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.SkillModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/*
********************** REQUIREMENTS *****************
* 1) when new skill comes to db, it should be inserted
* 2) when many skills comes to db, they all should be inserted
* 3) all skills should be returned when asked
* 4) all skills should be deleted when requested
* 5) when new skills are required to be replaced with old ones,
*  old ones should be deleted, new ones should be inserted and returned to user
* 6) insertManyAndReplace -> when user gives many skills,
* old ones should be deleted and new ones should be inserted
* 7) insertManyAndGet -> when new skills come to db
* old ones should be deleted, new ones inserted and returned
 */


@RunWith(AndroidJUnit4::class)
class SkillsDaoTest  {

    val SKILL1 = SkillModel(
        "1",
        "name 1",
        1,
        listOf("1", "2", "3")
    )

    val SKILL2 = SkillModel(
        "2",
        "name 2",
        1,
        listOf("21", "22", "23")
    )

    val SKILL3 = SkillModel(
        "3",
        "name 3",
        1,
        listOf("31", "32", "33")
    )

    val SKILLLIST = listOf(SKILL1, SKILL2, SKILL3)

    private lateinit var appDatabase: AppDatabase
    private lateinit var SUT: SkillsDao

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()

        SUT = appDatabase.getSkillsDao()
    }

    @Test
    fun insert_newSkill_skillInserted() = runBlocking {
        // arrange

        // act
        SUT.insert(SKILL1)
        val skillModels: List<SkillModel> =  SUT.getSkills()

        // assert
        assertEquals(skillModels.size, 1)
        assertEquals(skillModels[0], SKILL1)
    }

    @Test
    fun insertMany_newSkills_skillsInserted() = runBlocking {
        // arrange
        val skillsList = SKILLLIST

        // act
        SUT.insertMany(skillsList)
        val skillModels: List<SkillModel> =  SUT.getSkills()

        // assert
        assertEquals(skillModels.size, 3)
        assertEquals(skillModels, skillsList)
    }

    @Test
    fun deleteAll_delteAllSkills_returnEmptyList() = runBlocking {
        // arrange
        val skillsList = SKILLLIST
        SUT.insertMany(skillsList)

        // act
        SUT.deleteAll()
        val list = SUT.getSkills()

        // assert
        assertEquals(list.size, 0)
    }

    @Test
    fun insertManyAndReplace_newSkills_skillsInserted() = runBlocking {
        // arrange
        val skillsList1 = listOf(SKILL1, SKILL2)
        val skillsList2 = listOf(SKILL2, SKILL3)

        // act
        SUT.insertMany(skillsList1)
        SUT.insertManyAndReplace(skillsList2)
        val returnedList = SUT.getSkills()

        // assert
        assertEquals(returnedList, skillsList2)
    }

    @Test
    fun insertManyReplaceGet_newSkills_skillsInsertedAndReturned() = runBlocking {
        // arrange
        val skillsList1 = listOf(SKILL1, SKILL2)
        val skillsList2 = listOf(SKILL2, SKILL3)

        // act
        SUT.insertMany(skillsList1)
        val returnedList: List<SkillModel> = SUT.insertManyReplaceGet(skillsList2)

        // assert
        assertEquals(returnedList, skillsList2)
    }

    @Test
    fun insertGetSkills_skillsList_checkNotChanged() = runBlocking {
        // arrange
        val skill = SKILL1

        // act
        SUT.insert(skill)
        val skillList = SUT.getSkills()

        // assert
        assertEquals(skill.skillsList, skillList[0].skillsList)
    }

    @After
    fun close(){
        appDatabase.close()
    }
}



















