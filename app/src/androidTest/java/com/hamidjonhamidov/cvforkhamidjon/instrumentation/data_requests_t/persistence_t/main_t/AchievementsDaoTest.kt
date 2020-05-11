package com.hamidjonhamidov.cvforkhamidjon.instrumentation.data_requests_t.persistence_t.main_t

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.AchievementsDao
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AchievementModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.Honor
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

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


@RunWith(AndroidJUnit4::class)
class AchievementsDaoTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var SUT: AchievementsDao

    val HONOR1 = Honor(1, "title 1", "des 1", "link 1")
    val HONOR2 = Honor(2, "title 2", "des 2", "link 2")
    val HONOR3 = Honor(3, "title 3", "des 3", "link 3")
    val HONOR4 = Honor(4, "title 4", "des 4", "link 4")
    val HONOR5 = Honor(5, "title 5", "des 5", "link 5")
    val HONOR6 = Honor(6, "title 6", "des 6", "link 6")

    val ACHIEVEMENT1 = AchievementModel(1, "title 1", listOf(HONOR1, HONOR2))
    val ACHIEVEMENT2 = AchievementModel(2, "title 2", listOf(HONOR3, HONOR4))
    val ACHIEVEMENT3 = AchievementModel(3, "title 3", listOf(HONOR5, HONOR6))

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()

        SUT = appDatabase.getAchievementsDao()
    }

    @Test
    fun getAllAchievments_emptyList_returnEmptyList() = runBlocking {
        // arrange

        // act
        val list: List<AchievementModel> = SUT.getAllAchievements()

        // assert
        assertEquals(list.size, 0)
    }

    @Test
    fun insert_insertTwoItems_returnItems() = runBlocking {
        // arrange
        val item1 = ACHIEVEMENT1
        val item2 = ACHIEVEMENT2

        // act
        SUT.insert(item1)
        SUT.insert(item2)
        val list: List<AchievementModel> = SUT.getAllAchievements()

        // assert
        assertEquals(list.size, 2)
        assertEquals(list[0], item1)
        assertEquals(list[1], item2)
    }

    @Test
    fun insert_sameItems_assertSecondInserted() = runBlocking {
        // arrange
        val item1 = AchievementModel(1, "item1", listOf())
        val item2 = AchievementModel(1, "item2", listOf())

        // act
        SUT.insert(item1)
        SUT.insert(item2)
        val list = SUT.getAllAchievements()

        // assert
        assertEquals(list.size, 1)
        assertEquals(item2, list[0])
    }

    @Test
    fun insertMany_insertList_checkInsertion() = runBlocking {
        // arrange
        val list = listOf(ACHIEVEMENT1, ACHIEVEMENT2, ACHIEVEMENT3)

        // act
        SUT.insertMany(list)
        val returnedList = SUT.getAllAchievements()

        // assert
        assertEquals(returnedList, list)
    }

    @Test
    fun deleteAll_deleteMany_returnEmptyList() = runBlocking {
        // arrange
        SUT.insertMany(listOf(ACHIEVEMENT1, ACHIEVEMENT2, ACHIEVEMENT3))
        val listPrev = SUT.getAllAchievements()

        // act
        SUT.deleteAll()
        val listNext = SUT.getAllAchievements()

        // assert
        assertEquals(listPrev.size, 3)
        assertEquals(listNext.size, 0)
    }

    @Test
    fun insertManyAndReplace_manyInsertedExistingDeleted_checkNewList() = runBlocking {
        // arrange
        val list1 = listOf(ACHIEVEMENT1, ACHIEVEMENT2)
        val list2 = listOf(ACHIEVEMENT2, ACHIEVEMENT3)
        SUT.insertMany(list1)

        // act
        SUT.insertManyAndReplace(list2)
        val returnedList = SUT.getAllAchievements()

        // assert
        assertEquals(list2, returnedList)
    }

    @Test
    fun insertManyAndGet_manyInsertedExistingDeletedNewListReturned_returnNewOnlyListAndCheck() = runBlocking {
        // arrange
        val list1 = listOf(ACHIEVEMENT1, ACHIEVEMENT2)
        val list2 = listOf(ACHIEVEMENT2, ACHIEVEMENT3)
        SUT.insertMany(list1)

        // act
        val returnedList: List<AchievementModel> = SUT.insertManyAndGet(list2)


        // assert
        assertEquals(list2, returnedList)
    }


    @After
    fun closeDb(){
        appDatabase.close()
    }
}
























