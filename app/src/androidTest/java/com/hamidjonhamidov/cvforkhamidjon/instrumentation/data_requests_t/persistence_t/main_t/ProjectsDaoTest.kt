package com.hamidjonhamidov.cvforkhamidjon.instrumentation.data_requests_t.persistence_t.main_t

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.ProjectsDao
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AchievementModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.ProjectModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/*
********************** REQUIREMENTS *****************
* 0) when required, db should return all projects
* 1) when new project comes to db, it should be inserted
* 2) when many projects comes to db, they all should be inserted
* 3) all projects should be returned when asked
* 4) all projects should be deleted when requested
* 5) insertManyAndReplace -> when user gives many projects,
* old ones should be deleted and new ones should be inserted
* 6) insertManyAndGet -> when new projects come to db
* old ones should be deleted, new ones inserted and returned
 */

@RunWith(AndroidJUnit4::class)
class ProjectsDaoTest  {

    private lateinit var appDatabase: AppDatabase
    private lateinit var SUT: ProjectsDao

    val PROJECT1 = ProjectModel(1, "time 1", "title 1", "des 1", "link 1", "git 1")
    val PROJECT2 = ProjectModel(2, "time 2", "title 2", "des 2", "link 2","git 2")
    val PROJECT3 = ProjectModel(3, "time 3", "title 3", "des 3", "link 3", "git 3")
    val PROJECT4 = ProjectModel(4, "time 4", "title 4", "des 4", "link 4", "git 4")

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()

        SUT = appDatabase.getProjectsDao()
    }
    @Test
    fun getAllAchievments_emptyList_returnEmptyList() = runBlocking {
        // arrange

        // act
        val list: List<ProjectModel> = SUT.getAllProjects()

        // assert
        Assert.assertEquals(list.size, 0)
    }

    @Test
    fun insert_insertTwoItems_returnItems() = runBlocking {
        // arrange
        val item1 = PROJECT1
        val item2 = PROJECT2

        // act
        SUT.insert(item1)
        SUT.insert(item2)
        val list: List<ProjectModel> = SUT.getAllProjects()

        // assert
        Assert.assertEquals(list.size, 2)
        Assert.assertEquals(list[0], item1)
        Assert.assertEquals(list[1], item2)
    }

    @Test
    fun insert_sameItems_assertSecondInserted() = runBlocking {
        // arrange
        val item1 = PROJECT1
        val item2 = PROJECT2
        item2.projectId = item1.projectId

        // act
        SUT.insert(item1)
        SUT.insert(item2)
        val list = SUT.getAllProjects()

        // assert
        Assert.assertEquals(list.size, 1)
        Assert.assertEquals(item2, list[0])
    }

    @Test
    fun insertMany_insertList_checkInsertion() = runBlocking {
        // arrange
        val list = listOf(PROJECT1, PROJECT2, PROJECT3)

        // act
        SUT.insertMany(list)
        val returnedList = SUT.getAllProjects()

        // assert
        Assert.assertEquals(returnedList, list)
    }

    @Test
    fun deleteAll_deleteMany_returnEmptyList() = runBlocking {
        // arrange
        SUT.insertMany(listOf(PROJECT1, PROJECT2, PROJECT3))
        val listPrev = SUT.getAllProjects()

        // act
        SUT.deleteAll()
        val listNext = SUT.getAllProjects()

        // assert
        Assert.assertEquals(listPrev.size, 3)
        Assert.assertEquals(listNext.size, 0)
    }

    @Test
    fun insertManyAndReplace_manyInsertedExistingDeleted_checkNewList() = runBlocking {
        // arrange
        val list1 = listOf(PROJECT1, PROJECT2)
        val list2 = listOf(PROJECT3, PROJECT4)
        SUT.insertMany(list1)

        // act
        SUT.insertManyAndReplace(list2)
        val returnedList = SUT.getAllProjects()

        // assert
        Assert.assertEquals(list2, returnedList)
    }

    @Test
    fun insertManyAndGet_manyInsertedExistingDeletedNewListReturned_returnNewOnlyListAndCheck() = runBlocking {
        // arrange
        val list1 = listOf(PROJECT1, PROJECT2)
        val list2 = listOf(PROJECT3, PROJECT4)
        SUT.insertMany(list1)

        // act
        val returnedList: List<ProjectModel> = SUT.insertManyAndGet(list2)


        // assert
        Assert.assertEquals(list2, returnedList)
    }



    @After
    fun closeDb() {
        appDatabase.close()
    }
}




















