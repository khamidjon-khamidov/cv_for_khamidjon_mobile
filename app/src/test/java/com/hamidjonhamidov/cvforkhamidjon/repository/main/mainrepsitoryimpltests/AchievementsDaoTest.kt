package com.hamidjonhamidov.cvforkhamidjon.repository.main.mainrepsitoryimpltests

import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.main.MainApiService
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.AchievementsDao
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AchievementModel
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImpl
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.ACHIEVEMENT_MODEL_LIST
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.ACHIEVEMENT_REMOTE_MODEL1
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.ACHIEVEMENT_REMOTE_MODEL2
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.ACHIEVEMENT_REMOTE_MODEL_LIST
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.GETACHIEVEMENTS
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent.GetAchievements
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_ERROR_CACHE_EMPTY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_ERROR_CACHE_SUCCESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_NOT_ALLOWED_CACHE_EMPTY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_NOT_ALLOWED_CACHE_SUCCESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NO_INTERNET_CACHE_EMPTY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NO_INTERNET_CACHE_SUCCESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.Collections.list


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(JUnit4::class)
class AchievementsDaoTest {

    private val TAG = "UnitTesting"

    lateinit var SUT: MainRepositoryImpl

    @Mock
    lateinit var apiService: MainApiService

    @Mock
    lateinit var appDatabase: AppDatabase

    @Mock
    lateinit var achievevementsDaoTd: AchievmenentsDaoTd

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        achievevementsDaoTd =
            AchievmenentsDaoTd()

        SUT =
            MainRepositoryImpl(
                apiService,
                appDatabase
            )
    }


    @Test
    fun getAchievements_networkAvailableGetAchievementsEvent_achivementsFlowReturned_savedToDb() =
        runBlocking {
            // arrange
            println("Test started")

            val shouldToFragment = GETACHIEVEMENTS
            val shouldAchievevementModelList = ACHIEVEMENT_MODEL_LIST
            val shouldMessage = MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS.copy()

            `when`(appDatabase.getAchievementsDao())
                .thenReturn(achievevementsDaoTd)

            `when`(apiService.getAchievementsSync())
                .thenReturn(ACHIEVEMENT_REMOTE_MODEL_LIST)

            // act
            val result =
                SUT.getAchievements(
                    GetAchievements(),
                    true,
                    true
                )


            // assert

            result.onEach {
                assertEquals(it.toFragment, shouldToFragment)
                assertEquals(
                    it.data?.achievementsFragmentView?.achievements,
                    shouldAchievevementModelList
                )
                assertEquals(it.message, shouldMessage)
            }.launchIn(this)

            delay(1000)
            verify(apiService, times(1)).getAchievementsSync()
            assertEquals(achievevementsDaoTd.insertManyAndReplaceCalls, 1)
            assertEquals(achievevementsDaoTd.funCalls, 1)
            assertEquals(achievevementsDaoTd.inMemoryData.size, 5)
            Unit
        }

    @Test
    fun getAchievements_networkTimeoutCacheEmpty_returnCachedData() = runBlocking {
        // arrange
        NetworkConstants.NETWORK_DELAY = 6000 // make network delay long to throw exception
        achievevementsDaoTd.inMemoryData.addAll(ACHIEVEMENT_MODEL_LIST)
        `when`(appDatabase.getAchievementsDao())
            .thenReturn(achievevementsDaoTd)
        var onEachCalls = 0

        // act
        val result = SUT.getAchievements(
            GetAchievements(),
            true,
            true
        )

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS)
            assertEquals(it.toFragment, GETACHIEVEMENTS)
            assertEquals(it.data?.achievementsFragmentView?.achievements, ACHIEVEMENT_MODEL_LIST)
        }.launchIn(this)

        delay(7000)
        verifyNoMoreInteractions(apiService)
        assertEquals(onEachCalls, 1)
        assertEquals(achievevementsDaoTd.getAchievementCalls, 1)
        assertEquals(achievevementsDaoTd.funCalls, 1)
        assertEquals(achievevementsDaoTd.inMemoryData.size, 5)
        NetworkConstants.NETWORK_DELAY = 0
        Unit
    }

    @Test
    fun getAchievements_networkTimeOut_cacheEmpty() = runBlocking {
        // arrange
        NetworkConstants.NETWORK_DELAY = 6000
        `when`(appDatabase.getAchievementsDao())
            .thenReturn(achievevementsDaoTd)
        var onEachCalls = 0

        // act
        println("Before result")

        val result = SUT.getAchievements(
            GetAchievements(),
            true,
            true
        )

        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, NetworkConstants.MESSAGE_NETWORK_TIMEOUT_CACHE_EMPTY)
            assertEquals(it.toFragment, GETACHIEVEMENTS)
            assertEquals(it.data?.achievementsFragmentView?.achievements, null)
        }.launchIn(this)

        println("After onEach calls")

        delay(10000)
        assertEquals(onEachCalls, 1)
        verifyNoMoreInteractions(apiService)
        NetworkConstants.NETWORK_DELAY = 0
        Unit
    }

    @Test
    fun getAchievements_noInternetCacheSucces_returnCachedData() = runBlocking {
        // arrange
        achievevementsDaoTd.inMemoryData.addAll(ACHIEVEMENT_MODEL_LIST)
        `when`(appDatabase.getAchievementsDao())
            .thenReturn(achievevementsDaoTd)
        var onEachCalls = 0

        // act
        println("Before result")

        val result = SUT.getAchievements(
            GetAchievements(),
            false,
            true
        )

        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NO_INTERNET_CACHE_SUCCESS)
            assertEquals(it.toFragment, GETACHIEVEMENTS)
            assertEquals(it.data?.achievementsFragmentView?.achievements, ACHIEVEMENT_MODEL_LIST)
        }.launchIn(this)

        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verifyNoMoreInteractions(apiService)
        assertEquals(achievevementsDaoTd.funCalls, 1)
        assertEquals(achievevementsDaoTd.getAchievementCalls, 1)
        assertEquals(achievevementsDaoTd.inMemoryData.size, 5)
        Unit
    }

    @Test
    fun getAchievements_noInternetCacheEmpty_returnNull() = runBlocking {
        // arrange
        Mockito.`when`(appDatabase.getAchievementsDao())
            .thenReturn(achievevementsDaoTd)
        var onEachCalls = 0

        // act
        println("Before result")
        val result = SUT.getAchievements(
            GetAchievements(),
            false,
            true
        )

        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NO_INTERNET_CACHE_EMPTY)
            assertEquals(it.toFragment, GETACHIEVEMENTS)
            assertEquals(it.data?.achievementsFragmentView?.achievements, null)
        }.launchIn(this)

        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verifyNoMoreInteractions(apiService)
        assertEquals(achievevementsDaoTd.funCalls, 1)
        assertEquals(achievevementsDaoTd.getAchievementCalls, 1)
        assertEquals(achievevementsDaoTd.inMemoryData.size, 0)
        Unit
    }

    @Test
    fun getAchievements_NetworkErrorCacheSuccess_returnCachedData() = runBlocking {
        // arrange
        achievevementsDaoTd.inMemoryData.addAll(ACHIEVEMENT_MODEL_LIST)
        `when`(appDatabase.getAchievementsDao())
            .thenReturn(achievevementsDaoTd)

        doThrow(RuntimeException())
            .`when`(apiService).getAchievementsSync()

        var onEachCalls = 0

        // act
        println("Before result")
        val result = SUT.getAchievements(
            GetAchievements(),
            true,
            true
        )
        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NETWORK_ERROR_CACHE_SUCCESS)
            assertEquals(it.toFragment, GETACHIEVEMENTS)
            assertEquals(it.data?.achievementsFragmentView?.achievements, ACHIEVEMENT_MODEL_LIST)
        }.launchIn(this)
        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verify(apiService, times(1)).getAchievementsSync()
        assertEquals(achievevementsDaoTd.funCalls, 1)
        assertEquals(achievevementsDaoTd.getAchievementCalls, 1)
        assertEquals(achievevementsDaoTd.inMemoryData.size, 5)
        Unit
    }

    @Test
    fun getAchievements_NetworkErrorCacheEmpty_returnEmptyData() = runBlocking {
        // arrange
        `when`(appDatabase.getAchievementsDao())
            .thenReturn(achievevementsDaoTd)

        doThrow(RuntimeException())
            .`when`(apiService).getAchievementsSync()

        var onEachCalls = 0

        // act
        println("Before result")
        val result = SUT.getAchievements(
            GetAchievements(),
            true,
            true
        )
        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NETWORK_ERROR_CACHE_EMPTY)
            assertEquals(it.toFragment, GETACHIEVEMENTS)
            assertEquals(it.data?.achievementsFragmentView?.achievements, null)
        }.launchIn(this)
        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verify(apiService, times(1)).getAchievementsSync()
        assertEquals(achievevementsDaoTd.funCalls, 1)
        assertEquals(achievevementsDaoTd.getAchievementCalls, 1)
        assertEquals(achievevementsDaoTd.inMemoryData.size, 0)
        Unit
    }

    @Test
    fun getAchievements_networkNotAllowedCacheSuccess_returnCacheData() = runBlocking {
        // arrange
        achievevementsDaoTd.inMemoryData.addAll(ACHIEVEMENT_MODEL_LIST)
        `when`(appDatabase.getAchievementsDao())
            .thenReturn(achievevementsDaoTd)

        `when`(apiService.getAchievementsSync())
            .thenReturn(
                listOf(
                    ACHIEVEMENT_REMOTE_MODEL1.copy(listId = 4),
                    ACHIEVEMENT_REMOTE_MODEL2.copy(listId = 9)
                )
            )

        // act
        val result = SUT
            .getAchievements(
                GetAchievements(),
                false,
                false
            )

        // assert
        result.onEach {
            assertEquals(it.message, MESSAGE_NETWORK_NOT_ALLOWED_CACHE_SUCCESS)
            assertEquals(it.toFragment, GETACHIEVEMENTS)
            assertEquals(
                it.data?.achievementsFragmentView?.achievements,
                ACHIEVEMENT_MODEL_LIST
            )
        }.launchIn(this)

        delay(2000)
        verify(apiService, times(0)).getAchievementsSync()
        assertEquals(achievevementsDaoTd.funCalls, 1)
        assertEquals(achievevementsDaoTd.getAchievementCalls, 1)
        assertEquals(achievevementsDaoTd.inMemoryData.size, 5)
        Unit
    }

    @Test
    fun getAchievements_networkNotAllowedCacheEmpty_returnNull() = runBlocking {
        // arrange
        achievevementsDaoTd.inMemoryData.addAll(listOf())
        `when`(appDatabase.getAchievementsDao())
            .thenReturn(achievevementsDaoTd)

        `when`(apiService.getAchievementsSync())
            .thenReturn(listOf())

        // act
        val result = SUT
            .getAchievements(
                GetAchievements(),
                false,
                false
            )

        // assert
        result.onEach {
            assertEquals(it.message, MESSAGE_NETWORK_NOT_ALLOWED_CACHE_EMPTY)
            assertEquals(it.toFragment, GETACHIEVEMENTS)
            assertEquals(
                it.data?.achievementsFragmentView?.achievements,
                null
            )
        }.launchIn(this)

        delay(2000)
        verify(apiService, times(0)).getAchievementsSync()
        assertEquals(achievevementsDaoTd.funCalls, 1)
        assertEquals(achievevementsDaoTd.getAchievementCalls, 1)
        assertEquals(achievevementsDaoTd.inMemoryData.size, 0)
        Unit
    }

    ////////////////// Helper Classes ///////////////
    class AchievmenentsDaoTd : AchievementsDao {
        var funCalls = 0
        var insertCalls = 0
        var deleteAllCalls = 0
        var getAchievementCalls = 0
        var insertManyAndReplaceCalls = 0

        val inMemoryData = ArrayList<AchievementModel>()

        override suspend fun getAllAchievements(): List<AchievementModel> {
            funCalls++
            getAchievementCalls++
            return inMemoryData
        }

        override suspend fun insert(achievement: AchievementModel) {
            funCalls++
            insertCalls++
        }

        override suspend fun deleteAll() {
            funCalls++
            deleteAllCalls++
            inMemoryData.clear()
        }

        override suspend fun insertManyAndReplace(achievements: List<AchievementModel>) {
            funCalls++
            insertManyAndReplaceCalls++
            inMemoryData.clear()
            inMemoryData.addAll(achievements)
        }
    }

    /////////////////////////////////////////////////
}
