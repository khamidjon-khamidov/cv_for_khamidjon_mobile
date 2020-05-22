package com.hamidjonhamidov.cvforkhamidjon.repository.main.mainrepsitoryimpltests

import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.main.MainApiService
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.SkillsDao
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.SkillModel
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImpl
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.GETMYSKILLS
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.SKILLS_MODEL_LIST
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.SKILLS_REMOTE_MODEL_LIST
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.state.MainJobsEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.state.MainViewDestEvent
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


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(JUnit4::class)
class GetMySkillsTest {

    private val TAG = "UnitTesting"

    lateinit var SUT: MainRepositoryImpl

    @Mock
    lateinit var apiService: MainApiService

    @Mock
    lateinit var appDatabase: AppDatabase

    @Mock
    lateinit var skillsDaoTd: SkillsDaoTd

    lateinit var stateEvent: MainStateEvent

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        stateEvent = object : MainStateEvent {
            override val responsibleJob: MainJobsEvent
                get() = MainJobsEvent()
            override val destinationView: MainViewDestEvent
                get() = MainViewDestEvent.MySkillsFragmentDest()
        }
        skillsDaoTd =
            SkillsDaoTd()

        SUT =
            MainRepositoryImpl(
                apiService,
                appDatabase
            )
    }


    @Test
    fun getMySkills_networkAvailableGetMySkillsEvent_skillsFlowReturned_savedToDb() = runBlocking {
        // arrange
        println("Test started")

        val shouldToFragment = GETMYSKILLS
        val shouldSkillsModelList = SKILLS_MODEL_LIST
        val shouldMessage = MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS.copy()

        `when`(appDatabase.getSkillsDao())
            .thenReturn(skillsDaoTd)

        `when`(apiService.getSkillsSync())
            .thenReturn(SKILLS_REMOTE_MODEL_LIST)

        // act
        val result =
            SUT.getMySkills(
                stateEvent,
                true
            )


        // assert

        result.onEach {
            assertEquals(it.viewState?.mySkillsFragmentView?.mySkills, shouldSkillsModelList)
            assertEquals(it.message, shouldMessage)
        }.launchIn(this)

        delay(1000)
        verify(apiService, times(1)).getSkillsSync()
        assertEquals(skillsDaoTd.insertManyAndReplaceCalls, 1)
        assertEquals(skillsDaoTd.funCalls, 1)
        assertEquals(skillsDaoTd.inMemoryData.size, 5)
        Unit
    }

    @Test
    fun getMySkills_networkTimeoutCacheEmpty_returnCachedData() = runBlocking {
        // arrange
        NetworkConstants.NETWORK_DELAY = 6000 // make network delay long to throw exception
        skillsDaoTd.inMemoryData.addAll(SKILLS_MODEL_LIST)
        `when`(appDatabase.getSkillsDao())
            .thenReturn(skillsDaoTd)
        var onEachCalls = 0

        // act
        val result = SUT.getMySkills(
            stateEvent,
            true
        )

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS)
            assertEquals(it.viewState?.mySkillsFragmentView?.mySkills, SKILLS_MODEL_LIST)
        }.launchIn(this)

        delay(7000)
        verifyNoMoreInteractions(apiService)
        assertEquals(onEachCalls, 1)
        assertEquals(skillsDaoTd.getSkillsCalls, 1)
        assertEquals(skillsDaoTd.funCalls, 1)
        assertEquals(skillsDaoTd.inMemoryData.size, 5)
        NetworkConstants.NETWORK_DELAY = 0
        Unit
    }

    @Test
    fun getMySkills_networkTimeOut_cacheEmpty() = runBlocking {
        // arrange
        NetworkConstants.NETWORK_DELAY = 6000
        `when`(appDatabase.getSkillsDao())
            .thenReturn(skillsDaoTd)
        var onEachCalls =  0

        // act
        println("Before result")

        val result = SUT.getMySkills(
            stateEvent,
            true
        )

        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, NetworkConstants.MESSAGE_NETWORK_TIMEOUT_CACHE_EMPTY)
            assertEquals(it.viewState?.mySkillsFragmentView?.mySkills, null)
        }.launchIn(this)

        println("After onEach calls")

        delay(10000)
        assertEquals(onEachCalls, 1)
        verifyNoMoreInteractions(apiService)
        NetworkConstants.NETWORK_DELAY = 0
        Unit
    }

    @Test
    fun getMySkills_noInternetCacheSucces_returnCachedData() = runBlocking {
        // arrange
        skillsDaoTd.inMemoryData.addAll(SKILLS_MODEL_LIST)
        `when`(appDatabase.getSkillsDao())
            .thenReturn(skillsDaoTd)
        var onEachCalls =  0

        // act9
        println("Before result")

        val result = SUT.getMySkills(
            stateEvent,
            false
        )

        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NO_INTERNET_CACHE_SUCCESS)
            assertEquals(it.viewState?.mySkillsFragmentView?.mySkills, SKILLS_MODEL_LIST)
        }.launchIn(this)

        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verifyNoMoreInteractions(apiService)
        assertEquals(skillsDaoTd.funCalls, 1)
        assertEquals(skillsDaoTd.getSkillsCalls, 1)
        assertEquals(skillsDaoTd.inMemoryData.size, 5)
        Unit
    }

    @Test
    fun getMySkills_noInternetCacheEmpty_returnNull() = runBlocking {
        // arrange
        Mockito.`when`(appDatabase.getSkillsDao())
            .thenReturn(skillsDaoTd)
        var onEachCalls =  0

        // act
        println("Before result")
        val result = SUT.getMySkills(
            stateEvent,
            false
        )

        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NO_INTERNET_CACHE_EMPTY)
            assertEquals(it.viewState?.mySkillsFragmentView?.mySkills, null)
        }.launchIn(this)

        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verifyNoMoreInteractions(apiService)
        assertEquals(skillsDaoTd.funCalls, 1)
        assertEquals(skillsDaoTd.getSkillsCalls, 1)
        assertEquals(skillsDaoTd.inMemoryData.size, 0)
        Unit
    }

    @Test
    fun getMySkills_NetworkErrorCacheSuccess_returnCachedData() = runBlocking {
        // arrange
        skillsDaoTd.inMemoryData.addAll(SKILLS_MODEL_LIST)
        `when`(appDatabase.getSkillsDao())
            .thenReturn(skillsDaoTd)

        doThrow(RuntimeException())
            .`when`(apiService).getSkillsSync()

        var onEachCalls =  0

        // act
        println("Before result")
        val result = SUT.getMySkills(
            stateEvent,
            true
        )
        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NETWORK_ERROR_CACHE_SUCCESS)
            assertEquals(it.viewState?.mySkillsFragmentView?.mySkills, SKILLS_MODEL_LIST)
        }.launchIn(this)
        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verify(apiService, times(1)).getSkillsSync()
        assertEquals(skillsDaoTd.funCalls, 1)
        assertEquals(skillsDaoTd.getSkillsCalls, 1)
        assertEquals(skillsDaoTd.inMemoryData.size, 5)
        Unit
    }

    @Test
    fun getMySkills_NetworkErrorCacheEmpty_returnEmptyData() = runBlocking {
        // arrange
        `when`(appDatabase.getSkillsDao())
            .thenReturn(skillsDaoTd)

        doThrow(RuntimeException())
            .`when`(apiService).getSkillsSync()

        var onEachCalls =  0

        // act
        println("Before result")
        val result = SUT.getMySkills(
            stateEvent,
            true
        )
        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NETWORK_ERROR_CACHE_EMPTY)
            assertEquals(it.viewState?.mySkillsFragmentView?.mySkills, null)
        }.launchIn(this)
        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verify(apiService, times(1)).getSkillsSync()
        assertEquals(skillsDaoTd.funCalls, 1)
        assertEquals(skillsDaoTd.getSkillsCalls, 1)
        assertEquals(skillsDaoTd.inMemoryData.size, 0)
        Unit
    }

    @Test
    fun getMySkills_networkNotAllowedCacheSuccess_returnCacheData() = runBlocking {
        // arrange
        skillsDaoTd.inMemoryData.addAll(SKILLS_MODEL_LIST)
        `when`(appDatabase.getSkillsDao())
            .thenReturn(skillsDaoTd)

        // act
        val result = SUT
            .getMySkills(
                stateEvent,
                false,
                false
            )

        // assert
        result.onEach {
            assertEquals(it.message, MESSAGE_NETWORK_NOT_ALLOWED_CACHE_SUCCESS)
            assertEquals(
                it.viewState?.mySkillsFragmentView?.mySkills,
                SKILLS_MODEL_LIST
            )
        }.launchIn(this)

        delay(2000)
        verifyNoMoreInteractions(apiService)
        assertEquals(skillsDaoTd.funCalls, 1)
        assertEquals(skillsDaoTd.getSkillsCalls, 1)
        assertEquals(skillsDaoTd.inMemoryData.size, 5)
        Unit
    }

    @Test
    fun getMySkills_networkNotAllowedCacheEmpty_returnNull() = runBlocking {
        // arrange
        skillsDaoTd.inMemoryData.addAll(listOf())
        `when`(appDatabase.getSkillsDao())
            .thenReturn(skillsDaoTd)

        // act
        val result = SUT
            .getMySkills(
                stateEvent,
                false,
                false
            )

        // assert
        result.onEach {
            assertEquals(it.message, MESSAGE_NETWORK_NOT_ALLOWED_CACHE_EMPTY)
            assertEquals(
                it.viewState?.mySkillsFragmentView?.mySkills,
                null
            )
        }.launchIn(this)

        delay(2000)
        verifyNoMoreInteractions(apiService)
        assertEquals(skillsDaoTd.funCalls, 1)
        assertEquals(skillsDaoTd.getSkillsCalls, 1)
        assertEquals(skillsDaoTd.inMemoryData.size, 0)
        Unit
    }


    ////////////////// Helper Classes ///////////////
    class SkillsDaoTd : SkillsDao {
        var funCalls = 0
        var insertCalls = 0
        var deleteAllCalls = 0
        var getSkillsCalls = 0
        var insertManyAndReplaceCalls = 0

        val inMemoryData = ArrayList<SkillModel>()

        override suspend fun insert(skillModel: SkillModel) {
            funCalls++
            insertCalls++
        }

        override suspend fun getSkills(): List<SkillModel> {
            funCalls++
            getSkillsCalls++
            return inMemoryData
        }

        override fun deleteAll() {
            funCalls++
            deleteAllCalls++
            inMemoryData.clear()
        }

        override suspend fun insertManyAndReplace(skillsList: List<SkillModel>) {
            funCalls++
            insertManyAndReplaceCalls++
            inMemoryData.clear()
            inMemoryData.addAll(skillsList)
        }
    }

    /////////////////////////////////////////////////
}



















