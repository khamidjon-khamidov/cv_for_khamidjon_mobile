package com.hamidjonhamidov.cvforkhamidjon.repository.main.mainrepsitoryimpltests

import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.main.MainApiService
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.AboutMeDao
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.repository.Repository
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImpl
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.ABOUTMEMODEL
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.ABOUTMEREMOTEMODEL
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.GETHOME
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent.GetHome
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_ERROR_CACHE_EMPTY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_ERROR_CACHE_SUCCESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NO_INTERNET_CACHE_EMPTY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NO_INTERNET_CACHE_SUCCESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(JUnit4::class)
class GetAboutMeTest {

    private val TAG = "UnitTesting"

    lateinit var SUT: MainRepositoryImpl

    @Mock
    lateinit var apiService: MainApiService

    @Mock
    lateinit var appDatabase: AppDatabase

    @Mock
    lateinit var aboutMeDaoTd: AboutMeDaoTd

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        aboutMeDaoTd =
            AboutMeDaoTd()

        SUT =
            MainRepositoryImpl(
                apiService,
                appDatabase
            )
    }


    @Test
    fun getAboutMe_networkAvailableGetHomeEvent_aboutMeFlowReturned() = runBlocking {
        // arrange
        println("Test started")

        val shouldToFragment = GETHOME
        val shouldAboutMeModel = ABOUTMEMODEL
        val shouldMessage = MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS.copy()

        Mockito.`when`(appDatabase.getAboutMeDao())
            .thenReturn(aboutMeDaoTd)

        Mockito.`when`(apiService.getAboutMeSync())
            .thenReturn(listOf(ABOUTMEREMOTEMODEL))

        // act
        val result =
            SUT.getAboutMe(
                GetHome(),
                true
            )


        // assert

        result.onEach {
            assertEquals(it.toFragment, shouldToFragment)
            assertEquals(it.data?.homeFragmentView?.aboutMe, shouldAboutMeModel)
            assertEquals(it.message, shouldMessage)
        }.launchIn(this)

        delay(1000)
        verify(apiService, times(1)).getAboutMeSync()
        assertEquals(aboutMeDaoTd.replaceAboutMeCalls, 1)
        assertEquals(aboutMeDaoTd.funCalls, 1)
        assertEquals(aboutMeDaoTd.inMemoryData.size, 1)
        Unit
    }

    @Test
    fun getAboutMe_networkTimeoutCacheEmpty_returnCachedData() = runBlocking {
        // arrange
        NetworkConstants.NETWORK_DELAY = 6000 // make network delay long to throw exception
        aboutMeDaoTd.inMemoryData.add(ABOUTMEMODEL.copy())
        Mockito.`when`(appDatabase.getAboutMeDao())
            .thenReturn(aboutMeDaoTd)
        var onEachCalls = 0

        // act
        val result = SUT.getAboutMe(
            GetHome(),
            true
        )

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS)
            assertEquals(it.toFragment, GETHOME)
            assertEquals(it.data?.homeFragmentView?.aboutMe, ABOUTMEMODEL)
        }.launchIn(this)

        delay(7000)
        verifyNoMoreInteractions(apiService)
        assertEquals(onEachCalls, 1)
        assertEquals(aboutMeDaoTd.getAboutMeCalls, 1)
        assertEquals(aboutMeDaoTd.funCalls, 1)
        assertEquals(aboutMeDaoTd.inMemoryData.size, 1)
        NetworkConstants.NETWORK_DELAY = 0
        Unit
    }

    @Test
    fun getAboutMe_networkTimeOut_cacheEmpty() = runBlocking {
        // arrange
        NetworkConstants.NETWORK_DELAY = 6000
        Mockito.`when`(appDatabase.getAboutMeDao())
            .thenReturn(aboutMeDaoTd)
        var onEachCalls =  0

        // act
        println("Before result")

        val result = SUT.getAboutMe(
            GetHome(),
            true
        )

        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, NetworkConstants.MESSAGE_NETWORK_TIMEOUT_CACHE_EMPTY)
            assertEquals(it.toFragment, GETHOME)
            assertEquals(it.data?.homeFragmentView?.aboutMe, null)
        }.launchIn(this)

        println("After onEach calls")

        delay(10000)
        assertEquals(onEachCalls, 1)
        verifyNoMoreInteractions(apiService)
        NetworkConstants.NETWORK_DELAY = 0
        Unit
    }

    @Test
    fun getAboutMe_noInternetCacheSucces_returnCachedData() = runBlocking {
        // arrange
        aboutMeDaoTd.inMemoryData.add(ABOUTMEMODEL)
        Mockito.`when`(appDatabase.getAboutMeDao())
            .thenReturn(aboutMeDaoTd)
        var onEachCalls =  0

        // act
        println("Before result")

        val result = SUT.getAboutMe(
            GetHome(),
            false
        )

        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NO_INTERNET_CACHE_SUCCESS)
            assertEquals(it.toFragment, GETHOME)
            assertEquals(it.data?.homeFragmentView?.aboutMe, ABOUTMEMODEL.copy())
        }.launchIn(this)

        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verifyNoMoreInteractions(apiService)
        assertEquals(aboutMeDaoTd.funCalls, 1)
        assertEquals(aboutMeDaoTd.getAboutMeCalls, 1)
        assertEquals(aboutMeDaoTd.inMemoryData.size, 1)
        Unit
    }

    @Test
    fun getAboutMe_noInternetCacheEmpty_returnNull() = runBlocking {
        // arrange
        Mockito.`when`(appDatabase.getAboutMeDao())
            .thenReturn(aboutMeDaoTd)
        var onEachCalls =  0

        // act
        println("Before result")
        val result = SUT.getAboutMe(
            GetHome(),
            false
        )

        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NO_INTERNET_CACHE_EMPTY)
            assertEquals(it.toFragment, GETHOME)
            assertEquals(it.data?.homeFragmentView?.aboutMe, null)
        }.launchIn(this)

        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verifyNoMoreInteractions(apiService)
        assertEquals(aboutMeDaoTd.funCalls, 1)
        assertEquals(aboutMeDaoTd.getAboutMeCalls, 1)
        assertEquals(aboutMeDaoTd.inMemoryData.size, 0)
        Unit
    }

    @Test
    fun getAboutMe_NetworkErrorCacheSuccess_returnCachedData() = runBlocking {
        // arrange
        aboutMeDaoTd.inMemoryData.add(ABOUTMEMODEL.copy())
        Mockito.`when`(appDatabase.getAboutMeDao())
            .thenReturn(aboutMeDaoTd)

        doThrow(RuntimeException())
            .`when`(apiService).getAboutMeSync()

        var onEachCalls =  0

        // act
        println("Before result")
        val result = SUT.getAboutMe(
            GetHome(),
            true
        )
        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NETWORK_ERROR_CACHE_SUCCESS)
            assertEquals(it.toFragment, GETHOME)
            assertEquals(it.data?.homeFragmentView?.aboutMe, ABOUTMEMODEL)
        }.launchIn(this)
        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verify(apiService, times(1)).getAboutMeSync()
        assertEquals(aboutMeDaoTd.funCalls, 1)
        assertEquals(aboutMeDaoTd.getAboutMeCalls, 1)
        assertEquals(aboutMeDaoTd.inMemoryData.size, 1)
        Unit
    }

    @Test
    fun getAboutMe_NetworkErrorCacheEmpty_returnEmptyData() = runBlocking {
        // arrange
        Mockito.`when`(appDatabase.getAboutMeDao())
            .thenReturn(aboutMeDaoTd)

        doThrow(RuntimeException())
            .`when`(apiService).getAboutMeSync()

        var onEachCalls =  0

        // act
        println("Before result")
        val result = SUT.getAboutMe(
            GetHome(),
            true
        )
        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NETWORK_ERROR_CACHE_EMPTY)
            assertEquals(it.toFragment, GETHOME)
            assertEquals(it.data?.homeFragmentView?.aboutMe, null)
        }.launchIn(this)
        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verify(apiService, times(1)).getAboutMeSync()
        assertEquals(aboutMeDaoTd.funCalls, 1)
        assertEquals(aboutMeDaoTd.getAboutMeCalls, 1)
        assertEquals(aboutMeDaoTd.inMemoryData.size, 0)
        Unit
    }

    ////////////////// Helper Classes ///////////////
    class AboutMeDaoTd : AboutMeDao {
        var funCalls = 0
        var insertCalls = 0
        var deleteAllCalls = 0
        var getAboutMeCalls = 0
        var replaceAboutMeCalls = 0

        val inMemoryData = ArrayList<AboutMeModel>()


        override suspend fun insert(aboutMeModel: AboutMeModel) {
            inMemoryData.add(aboutMeModel)
            insertCalls++
            funCalls++
        }

        override suspend fun deleteAll() {
            inMemoryData.clear()
            funCalls++
            deleteAllCalls++
        }

        override suspend fun getAboutMe(): List<AboutMeModel> {
            funCalls++
            getAboutMeCalls++
            return inMemoryData
        }

        override suspend fun replaceAboutMe(aboutMeModel: AboutMeModel) {
            funCalls++
            replaceAboutMeCalls++
            inMemoryData.add(aboutMeModel)
        }

    }

    /////////////////////////////////////////////////
}



















