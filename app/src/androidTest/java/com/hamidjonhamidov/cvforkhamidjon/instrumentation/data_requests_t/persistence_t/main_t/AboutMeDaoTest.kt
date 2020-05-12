package com.hamidjonhamidov.cvforkhamidjon.instrumentation.data_requests_t.persistence_t.main_t

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.AboutMeDao
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.Education
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AboutMeDaoTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var SUT: AboutMeDao

    val EDUCATION1 =
        Education(
            "name 1",
            "link 1"
        )
    val EDUCATION2 =
        Education(
            "name 2",
            "link 2"
        )
    val EDUCATION3 =
        Education(
            "name 3",
            "link 3"
        )
    val EDUCATION4 =
        Education(
            "name 4",
            "link 4"
        )

    val EDULIST = listOf(EDUCATION1, EDUCATION2, EDUCATION3, EDUCATION4)

    val ABOUTMEINFO =
        AboutMeModel(
            "Sep 14, 1998",
            "some address",
            "someemail",
            "some phone",
            EDULIST,
            "some description"
        )

    val ABOUTMEINFO2 =
        AboutMeModel(
            "Sep 14, 1998 2",
            "some address 2",
            "someemail 2",
            "some phone 2",
            EDULIST,
            "some description 2"
        )

    val ABOUTMEINFO3 =
        AboutMeModel(
            "Sep 14, 1998 3",
            "some address 3",
            "someemail 3",
            "some phone 3",
            EDULIST,
            "some description 3"
        )

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()

        SUT = appDatabase.getAboutMeDao()
    }

    @Test
    fun getAboutMe_empty_returnEmptyData() = runBlocking {
        // arrange

        // act
        val list = SUT.getAboutMe()

        // assert
        assertEquals(list.size, 0)
    }

    @Test
    fun getInsertAboutMe_assertEquals_getCorrectData() = runBlocking {
        // arrange

        // act
        SUT.replaceAboutMe(ABOUTMEINFO)
        val aboutme = SUT.getAboutMe()

        // assert
        assertEquals(ABOUTMEINFO, aboutme[0])
        assertEquals(aboutme.size, 1)
    }

    @Test
    fun replaceAboutMe_replaceManyTimes_assertSizeEqualsOne() = runBlocking {
        // arrange
        SUT.replaceAboutMe(ABOUTMEINFO)
        SUT.replaceAboutMe(ABOUTMEINFO2)
        SUT.replaceAboutMe(ABOUTMEINFO3)

        // act
        val list = SUT.getAboutMe()

        // assert
        assertEquals(list.size, 1)
        assertEquals(list[0], ABOUTMEINFO3)
    }

    @Test
    fun deleteAll_replaceManyTimes_listSizeZero() = runBlocking {
        // arrange
        SUT.replaceAboutMe(ABOUTMEINFO)
        SUT.replaceAboutMe(ABOUTMEINFO2)
        SUT.replaceAboutMe(ABOUTMEINFO3)
        SUT.deleteAll()

        // act
        val list = SUT.getAboutMe()

        // assert
        assertEquals(list.size, 0)
    }

    @Test
    fun replaceAboutMeAndGet_dataBaseWithElements_replaceWithNewOne_returnNewElements() = runBlocking {
        // arrange
        SUT.replaceAboutMe(ABOUTMEINFO)
        SUT.replaceAboutMe(ABOUTMEINFO2)

        // act
        val list = SUT.replaceAboutMeAndGet(ABOUTMEINFO3)

        // assert
        assertEquals(list.size, 1)
        assertEquals(list[0], ABOUTMEINFO3)
    }

    @Test
    fun replaceAboutMeAndGet_dataBaseEmpty_replaceWithNewOne_returnNewElements() = runBlocking {
        // arrange

        // act
        val list = SUT.replaceAboutMeAndGet(ABOUTMEINFO3)

        // assert
        assertEquals(list.size, 1)
        assertEquals(list[0], ABOUTMEINFO3)
    }

    // insert element in database and assert it return correct data elements
    @Test
    fun replaceAboutMe_convert_assertArrayConversion() = runBlocking {
        // arrange
        SUT.replaceAboutMe(ABOUTMEINFO)

        // act
        val list = SUT.getAboutMe()
        val eduList = list[0].education

        // assert
        assertEquals(eduList.size, EDULIST.size)
        assertEquals(eduList[0], EDULIST[0])
        assertEquals(eduList[1], EDULIST[1])
        assertEquals(eduList[2], EDULIST[2])
    }

    @After
    fun close() {
        appDatabase.close()
    }
}





















