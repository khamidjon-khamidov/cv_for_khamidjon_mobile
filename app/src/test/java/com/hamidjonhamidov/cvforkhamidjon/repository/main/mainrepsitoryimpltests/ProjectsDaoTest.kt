package com.hamidjonhamidov.cvforkhamidjon.repository.main.mainrepsitoryimpltests

import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.main.MainApiService
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.ProjectsDao
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.ProjectModel
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImpl
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.GETPROJECT
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.PROJECT_MODEL_LIST
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.PROJECT_REMOTE_MODEL_LIST
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent.GetProjects
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_ERROR_CACHE_EMPTY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_ERROR_CACHE_SUCCESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_NOT_ALLOWED_CACHE_EMPTY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_NOT_ALLOWED_CACHE_SUCCESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_TIMEOUT_CACHE_EMPTY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NO_INTERNET_CACHE_EMPTY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NO_INTERNET_CACHE_SUCCESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Assert
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
class ProjectsDaoTest {

    private val TAG = "UnitTesting"

    lateinit var SUT: MainRepositoryImpl

    @Mock
    lateinit var apiService: MainApiService

    @Mock
    lateinit var appDatabase: AppDatabase

    @Mock
    lateinit var projectsDaoTd: ProjectesDaoTd

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        projectsDaoTd =
            ProjectesDaoTd()

        SUT =
            MainRepositoryImpl(
                apiService,
                appDatabase
            )
    }


    @Test
    fun getProjects_networkAvailableGetAchievementsEvent_achivementsFlowReturned_savedToDb() = runBlocking {
        // arrange
        println("Test started")

        val shouldToFragment = GETPROJECT
        val shouldProjectModelList = PROJECT_MODEL_LIST
        val shouldMessage = MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS.copy()

        `when`(appDatabase.getProjectsDao())
            .thenReturn(projectsDaoTd)

        `when`(apiService.getProjectsSync())
            .thenReturn(PROJECT_REMOTE_MODEL_LIST)

        // act
        val result =
            SUT.getProjects(
                GetProjects(),
                true
            )


        // assert

        result.onEach {
            Assert.assertEquals(it.toFragment, shouldToFragment)
            Assert.assertEquals(it.data?.projectsFragmentView?.projects, shouldProjectModelList)
            Assert.assertEquals(it.message, shouldMessage)
        }.launchIn(this)

        delay(1000)
        verify(apiService, times(1)).getProjectsSync()
        Assert.assertEquals(projectsDaoTd.insertManyAndReplaceCalls, 1)
        Assert.assertEquals(projectsDaoTd.funCalls, 1)
        Assert.assertEquals(projectsDaoTd.inMemoryData.size, 4)
        Unit
    }

    @Test
    fun getProjects_networkTimeoutCacheEmpty_returnCachedData() = runBlocking {
        // arrange
        NetworkConstants.NETWORK_DELAY = 6000 // make network delay long to throw exception
        projectsDaoTd.inMemoryData.addAll(PROJECT_MODEL_LIST)
        `when`(appDatabase.getProjectsDao())
            .thenReturn(projectsDaoTd)
        var onEachCalls = 0

        // act
        val result = SUT.getProjects(
            GetProjects(),
            true
        )

        // assert
        result.onEach {
            onEachCalls++
            Assert.assertEquals(it.message, MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS)
            Assert.assertEquals(it.toFragment, GETPROJECT)
            Assert.assertEquals(it.data?.projectsFragmentView?.projects, PROJECT_MODEL_LIST)
        }.launchIn(this)

        delay(7000)
        verifyNoMoreInteractions(apiService)
        Assert.assertEquals(onEachCalls, 1)
        Assert.assertEquals(projectsDaoTd.getProjectsCalls, 1)
        Assert.assertEquals(projectsDaoTd.funCalls, 1)
        Assert.assertEquals(projectsDaoTd.inMemoryData.size, 4)
        NetworkConstants.NETWORK_DELAY = 0
        Unit
    }

    @Test
    fun getProjects_networkTimeOut_cacheEmpty() = runBlocking {
        // arrange
        NetworkConstants.NETWORK_DELAY = 6000
        `when`(appDatabase.getProjectsDao())
            .thenReturn(projectsDaoTd)
        var onEachCalls =  0

        // act
        println("Before result")

        val result = SUT.getProjects(
            GetProjects(),
            true
        )

        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            Assert.assertEquals(it.message, MESSAGE_NETWORK_TIMEOUT_CACHE_EMPTY)
            Assert.assertEquals(it.toFragment, GETPROJECT)
            Assert.assertEquals(it.data?.projectsFragmentView?.projects, null)
        }.launchIn(this)

        println("After onEach calls")

        delay(10000)
        Assert.assertEquals(onEachCalls, 1)
        verifyNoMoreInteractions(apiService)
        NetworkConstants.NETWORK_DELAY = 0
        Unit
    }

    @Test
    fun getProjects_noInternetCacheSucces_returnCachedData() = runBlocking {
        // arrange
        projectsDaoTd.inMemoryData.addAll(PROJECT_MODEL_LIST)
        `when`(appDatabase.getProjectsDao())
            .thenReturn(projectsDaoTd)
        var onEachCalls =  0

        // act
        println("Before result")

        val result = SUT.getProjects(
            GetProjects(),
            false
        )

        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            Assert.assertEquals(it.message, MESSAGE_NO_INTERNET_CACHE_SUCCESS)
            Assert.assertEquals(it.toFragment, GETPROJECT)
            Assert.assertEquals(it.data?.projectsFragmentView?.projects, PROJECT_MODEL_LIST)
        }.launchIn(this)

        println("After onEach calls")

        delay(2000)
        Assert.assertEquals(onEachCalls, 1)
        verifyNoMoreInteractions(apiService)
        Assert.assertEquals(projectsDaoTd.funCalls, 1)
        Assert.assertEquals(projectsDaoTd.getProjectsCalls, 1)
        Assert.assertEquals(projectsDaoTd.inMemoryData.size, 4)
        Unit
    }

    @Test
    fun getProjects_noInternetCacheEmpty_returnNull() = runBlocking {
        // arrange
        `when`(appDatabase.getProjectsDao())
            .thenReturn(projectsDaoTd)
        var onEachCalls =  0

        // act
        println("Before result")
        val result = SUT.getProjects(
            GetProjects(),
            false
        )

        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            Assert.assertEquals(it.message, MESSAGE_NO_INTERNET_CACHE_EMPTY)
            Assert.assertEquals(it.toFragment, GETPROJECT)
            Assert.assertEquals(it.data?.projectsFragmentView?.projects, null)
        }.launchIn(this)

        println("After onEach calls")

        delay(2000)
        Assert.assertEquals(onEachCalls, 1)
        verifyNoMoreInteractions(apiService)
        Assert.assertEquals(projectsDaoTd.funCalls, 1)
        Assert.assertEquals(projectsDaoTd.getProjectsCalls, 1)
        Assert.assertEquals(projectsDaoTd.inMemoryData.size, 0)
        Unit
    }

    @Test
    fun getProjects_NetworkErrorCacheSuccess_returnCachedData() = runBlocking {
        // arrange
        projectsDaoTd.inMemoryData.addAll(PROJECT_MODEL_LIST)
        `when`(appDatabase.getProjectsDao())
            .thenReturn(projectsDaoTd)

        doThrow(RuntimeException())
            .`when`(apiService).getProjectsSync()

        var onEachCalls =  0

        // act
        println("Before result")
        val result = SUT.getProjects(
            GetProjects(),
            true
        )
        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            Assert.assertEquals(it.message, MESSAGE_NETWORK_ERROR_CACHE_SUCCESS)
            Assert.assertEquals(it.toFragment, GETPROJECT)
            Assert.assertEquals(it.data?.projectsFragmentView?.projects, PROJECT_MODEL_LIST)
        }.launchIn(this)
        println("After onEach calls")

        delay(2000)
        Assert.assertEquals(onEachCalls, 1)
        verify(apiService, times(1)).getProjectsSync()
        Assert.assertEquals(projectsDaoTd.funCalls, 1)
        Assert.assertEquals(projectsDaoTd.getProjectsCalls, 1)
        Assert.assertEquals(projectsDaoTd.inMemoryData.size, 4)
        Unit
    }

    @Test
    fun getProjects_NetworkErrorCacheEmpty_returnEmptyData() = runBlocking {
        // arrange
        `when`(appDatabase.getProjectsDao())
            .thenReturn(projectsDaoTd)

        doThrow(RuntimeException())
            .`when`(apiService).getProjectsSync()

        var onEachCalls =  0

        // act
        println("Before result")
        val result = SUT.getProjects(
            GetProjects(),
            true
        )
        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            Assert.assertEquals(it.message, MESSAGE_NETWORK_ERROR_CACHE_EMPTY)
            Assert.assertEquals(it.toFragment, GETPROJECT)
            Assert.assertEquals(it.data?.projectsFragmentView?.projects, null)
        }.launchIn(this)
        println("After onEach calls")

        delay(2000)
        Assert.assertEquals(onEachCalls, 1)
        verify(apiService, times(1)).getProjectsSync()
        Assert.assertEquals(projectsDaoTd.funCalls, 1)
        Assert.assertEquals(projectsDaoTd.getProjectsCalls, 1)
        Assert.assertEquals(projectsDaoTd.inMemoryData.size, 0)
        Unit
    }

    @Test
    fun getProjects_networkNotAllowedCacheSuccess_returnCacheData() = runBlocking {
        // arrange
        projectsDaoTd.inMemoryData.addAll(PROJECT_MODEL_LIST)
        `when`(appDatabase.getProjectsDao())
            .thenReturn(projectsDaoTd)

        // act
        val result = SUT
            .getProjects(
                GetProjects(),
                false,
                false
            )

        // assert
        result.onEach {
            assertEquals(it.message, MESSAGE_NETWORK_NOT_ALLOWED_CACHE_SUCCESS)
            assertEquals(it.toFragment, GETPROJECT)
            assertEquals(
                it.data?.projectsFragmentView?.projects,
                PROJECT_MODEL_LIST
            )
        }.launchIn(this)

        delay(2000)
        verifyNoMoreInteractions(apiService)
        assertEquals(projectsDaoTd.funCalls, 1)
        assertEquals(projectsDaoTd.getProjectsCalls, 1)
        assertEquals(projectsDaoTd.inMemoryData.size, 4)
        Unit
    }

    @Test
    fun getProjects_networkNotAllowedCacheEmpty_returnNull() = runBlocking {
        // arrange
        projectsDaoTd.inMemoryData.addAll(listOf())
        `when`(appDatabase.getProjectsDao())
            .thenReturn(projectsDaoTd)

        // act
        val result = SUT
            .getProjects(
                GetProjects(),
                false,
                false
            )

        // assert
        result.onEach {
            assertEquals(it.message, MESSAGE_NETWORK_NOT_ALLOWED_CACHE_EMPTY)
            assertEquals(it.toFragment, GETPROJECT)
            assertEquals(
                it.data?.projectsFragmentView?.projects,
                null
            )
        }.launchIn(this)

        delay(2000)
        verifyNoMoreInteractions(apiService)
        assertEquals(projectsDaoTd.funCalls, 1)
        assertEquals(projectsDaoTd.getProjectsCalls, 1)
        assertEquals(projectsDaoTd.inMemoryData.size, 0)
        Unit
    }

    ////////////////// Helper Classes ///////////////
    class ProjectesDaoTd : ProjectsDao {
        var funCalls = 0
        var insertCalls = 0
        var deleteAllCalls = 0
        var getProjectsCalls = 0
        var insertManyAndReplaceCalls = 0

        val inMemoryData = ArrayList<ProjectModel>()

        override suspend fun getAllProjects(): List<ProjectModel> {
            funCalls++
            getProjectsCalls++
            return inMemoryData

        }

        override suspend fun insert(project: ProjectModel) {
            insertCalls++
        }

        override suspend fun deleteAll() {
            funCalls++
            deleteAllCalls++
        }

        override suspend fun insertManyAndReplace(projects: List<ProjectModel>) {
            funCalls++
            insertManyAndReplaceCalls++
            inMemoryData.clear()
            inMemoryData.addAll(projects)
        }
    }

    /////////////////////////////////////////////////
}