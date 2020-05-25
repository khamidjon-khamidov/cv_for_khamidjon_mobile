package com.hamidjonhamidov.cvforkhamidjon.repository.main.mainrepsitoryimpltests

import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.main.MainApiService
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main.PostsDao
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.PostModel
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImpl
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.POST_MODEL_LIST
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImplTestConstants.POST_REMOTE_LIST
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.state.MainJobsEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.state.MainViewDestEvent
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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(JUnit4::class)
class PostsDaoTest {

    private val TAG = "UnitTesting"

    lateinit var SUT: MainRepositoryImpl

    @Mock
    lateinit var apiService: MainApiService

    @Mock
    lateinit var appDatabase: AppDatabase

    @Mock
    lateinit var postDaoTd: PostsDaoTD

    lateinit var stateEvent: MainStateEvent

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        stateEvent = object : MainStateEvent{
            override val responsibleJob: MainJobsEvent
                get() = MainJobsEvent.GetPosts()
            override val destinationView: MainViewDestEvent
                get() = MainViewDestEvent.GetPostsFragmentDest()
        }

        postDaoTd =
            PostsDaoTD()

        SUT =
            MainRepositoryImpl(
                apiService,
                appDatabase
            )
    }


    @Test
    fun getPostss_networkAvailablePostsEvent_postsFlowReturned_savedToDb() = runBlocking {
        // arrange
        println("Test started")

        val shouldPostModelList = POST_MODEL_LIST
        val shouldMessage = MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS.copy()

        `when`(appDatabase.getPostsDao())
            .thenReturn(postDaoTd)

        `when`(apiService.getPostsSync())
            .thenReturn(POST_REMOTE_LIST)

        // act
        val result =
            SUT.getPosts(
                stateEvent,
                true
            )


        // assert

        result.onEach {
            assertEquals(it.viewState?.postsFragmentView?.posts, shouldPostModelList)
            assertEquals(it.message, shouldMessage)
        }.launchIn(this)

        delay(1000)
        verify(apiService, times(1)).getPostsSync()
        assertEquals(postDaoTd.insertManyAndReplaceCalls, 1)
        assertEquals(postDaoTd.funCalls, 1)
        assertEquals(postDaoTd.inMemoryData.size, 4)
        Unit
    }

    @Test
    fun getPosts_networkTimeoutCacheEmpty_returnCachedData() = runBlocking {
        // arrange
        NetworkConstants.NETWORK_DELAY = 6000 // make network delay long to throw exception
        postDaoTd.inMemoryData.addAll(POST_MODEL_LIST)
        `when`(appDatabase.getPostsDao())
            .thenReturn(postDaoTd)
        var onEachCalls = 0

        // act
        val result = SUT.getPosts(
            stateEvent,
            true
        )

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS)
            assertEquals(it.viewState?.postsFragmentView?.posts, POST_MODEL_LIST)
        }.launchIn(this)

        delay(7000)
        verifyNoMoreInteractions(apiService)
        assertEquals(onEachCalls, 1)
        assertEquals(postDaoTd.getProjectsCalls, 1)
        assertEquals(postDaoTd.funCalls, 1)
        assertEquals(postDaoTd.inMemoryData.size, 4)
        NetworkConstants.NETWORK_DELAY = 0
        Unit
    }

    @Test
    fun getPosts_networkTimeOut_cacheEmpty() = runBlocking {
        // arrange
        NetworkConstants.NETWORK_DELAY = 6000
        `when`(appDatabase.getPostsDao())
            .thenReturn(postDaoTd)
        var onEachCalls =  0

        // act
        println("Before result")

        val result = SUT.getPosts(
            stateEvent,
            true
        )

        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NETWORK_TIMEOUT_CACHE_EMPTY)
            assertEquals(it.viewState?.postsFragmentView?.posts, null)
        }.launchIn(this)

        println("After onEach calls")

        delay(10000)
        assertEquals(onEachCalls, 1)
        verifyNoMoreInteractions(apiService)
        NetworkConstants.NETWORK_DELAY = 0
        Unit
    }

    @Test
    fun getPosts_noInternetCacheSucces_returnCachedData() = runBlocking {
        // arrange
        postDaoTd.inMemoryData.addAll(POST_MODEL_LIST)
        `when`(appDatabase.getPostsDao())
            .thenReturn(postDaoTd)
        var onEachCalls =  0

        // act
        println("Before result")

        val result = SUT.getPosts(
            stateEvent,
            false
        )

        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NO_INTERNET_CACHE_SUCCESS)
            assertEquals(it.viewState?.postsFragmentView?.posts, POST_MODEL_LIST)
        }.launchIn(this)

        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verifyNoMoreInteractions(apiService)
        assertEquals(postDaoTd.funCalls, 1)
        assertEquals(postDaoTd.getProjectsCalls, 1)
        assertEquals(postDaoTd.inMemoryData.size, 4)
        Unit
    }

    @Test
    fun getPosts_noInternetCacheEmpty_returnNull() = runBlocking {
        // arrange
        `when`(appDatabase.getPostsDao())
            .thenReturn(postDaoTd)
        var onEachCalls =  0

        // act
        println("Before result")
        val result = SUT.getPosts(
            stateEvent,
            false
        )

        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NO_INTERNET_CACHE_EMPTY)
            assertEquals(it.viewState?.postsFragmentView?.posts, null)
        }.launchIn(this)

        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verifyNoMoreInteractions(apiService)
        assertEquals(postDaoTd.funCalls, 1)
        assertEquals(postDaoTd.getProjectsCalls, 1)
        assertEquals(postDaoTd.inMemoryData.size, 0)
        Unit
    }

    @Test
    fun getPostss_NetworkErrorCacheSuccess_returnCachedData() = runBlocking {
        // arrange
        postDaoTd.inMemoryData.addAll(POST_MODEL_LIST)
        `when`(appDatabase.getPostsDao())
            .thenReturn(postDaoTd)

        doThrow(RuntimeException())
            .`when`(apiService).getPostsSync()

        var onEachCalls =  0

        // act
        println("Before result")
        val result = SUT.getPosts(
            stateEvent,
            true
        )
        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(MESSAGE_NETWORK_ERROR_CACHE_SUCCESS, it.message)
            assertEquals(it.viewState?.postsFragmentView?.posts, POST_MODEL_LIST)
        }.launchIn(this)
        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verify(apiService, times(1)).getPostsSync()
        assertEquals(postDaoTd.funCalls, 1)
        assertEquals(postDaoTd.getProjectsCalls, 1)
        assertEquals(postDaoTd.inMemoryData.size, 4)
        Unit
    }

    @Test
    fun getPosts_NetworkErrorCacheEmpty_returnEmptyData() = runBlocking {
        // arrange
        `when`(appDatabase.getPostsDao())
            .thenReturn(postDaoTd)

        doThrow(RuntimeException())
            .`when`(apiService).getPostsSync()

        var onEachCalls =  0

        // act
        println("Before result")
        val result = SUT.getPosts(
            stateEvent,
            true
        )
        println("After result")

        // assert
        result.onEach {
            onEachCalls++
            assertEquals(it.message, MESSAGE_NETWORK_ERROR_CACHE_EMPTY)
            assertEquals(it.viewState?.postsFragmentView?.posts, null)
        }.launchIn(this)
        println("After onEach calls")

        delay(2000)
        assertEquals(onEachCalls, 1)
        verify(apiService, times(1)).getPostsSync()
        assertEquals(postDaoTd.funCalls, 1)
        assertEquals(postDaoTd.getProjectsCalls, 1)
        assertEquals(postDaoTd.inMemoryData.size, 0)
        Unit
    }

    @Test
    fun getPosts_networkNotAllowedCacheSuccess_returnCacheData() = runBlocking {
        // arrange
        postDaoTd.inMemoryData.addAll(POST_MODEL_LIST)
        `when`(appDatabase.getPostsDao())
            .thenReturn(postDaoTd)

        // act
        val result = SUT
            .getPosts(
                stateEvent,
                false,
                false
            )

        // assert
        result.onEach {
            assertEquals(it.message, MESSAGE_NETWORK_NOT_ALLOWED_CACHE_SUCCESS)
            assertEquals(
                it.viewState?.postsFragmentView?.posts,
                POST_MODEL_LIST
            )
        }.launchIn(this)

        delay(2000)
        verifyNoMoreInteractions(apiService)
        assertEquals(postDaoTd.funCalls, 1)
        assertEquals(postDaoTd.getProjectsCalls, 1)
        assertEquals(postDaoTd.inMemoryData.size, 4)
        Unit
    }

    @Test
    fun getPosts_networkNotAllowedCacheEmpty_returnNull() = runBlocking {
        // arrange
        postDaoTd.inMemoryData.addAll(listOf())
        `when`(appDatabase.getPostsDao())
            .thenReturn(postDaoTd)

        // act
        val result = SUT
            .getPosts(
                stateEvent,
                false,
                false
            )

        // assert
        result.onEach {
            assertEquals(it.message, MESSAGE_NETWORK_NOT_ALLOWED_CACHE_EMPTY)
            assertEquals(
                it.viewState?.postsFragmentView?.posts,
                null
            )
        }.launchIn(this)

        delay(2000)
        verifyNoMoreInteractions(apiService)
        assertEquals(postDaoTd.funCalls, 1)
        assertEquals(postDaoTd.getProjectsCalls, 1)
        assertEquals(postDaoTd.inMemoryData.size, 0)
        Unit
    }

    ////////////////// Helper Classes ///////////////
    class PostsDaoTD : PostsDao {
        var funCalls = 0
        var insertCalls = 0
        var deleteAllCalls = 0
        var getProjectsCalls = 0
        var insertManyAndReplaceCalls = 0

        val inMemoryData = ArrayList<PostModel>()

        override suspend fun getAllPosts(): List<PostModel> {
            funCalls++
            getProjectsCalls++
            return inMemoryData
        }

        override suspend fun insert(post: PostModel) {
            insertCalls++
        }

        override suspend fun deleteAll() {
            funCalls++
            deleteAllCalls++
        }

        override suspend fun insertManyAndReplace(posts: List<PostModel>) {
            funCalls++
            insertManyAndReplaceCalls++
            inMemoryData.clear()
            inMemoryData.addAll(posts)
        }
    }

    /////////////////////////////////////////////////
}