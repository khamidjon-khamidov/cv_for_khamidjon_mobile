package com.hamidjonhamidov.cvforkhamidjon.instrumentation.data_requests_t.persistence_t.contact

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.contact.ContactsDao
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel.Companion.STATUS_NOT_SENT
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel.Companion.STATUS_SENT
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel.Companion.WHO_HIM
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel.Companion.WHO_ME
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.NotificationsModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ConstactsDaoTest {

    val MESSAGE_1 = MessageModel(4, WHO_ME, "msg1", STATUS_NOT_SENT)
    val MESSAGE_2 = MessageModel(1, WHO_HIM, "msg2", STATUS_NOT_SENT)
    val MESSAGE_3 = MessageModel(5, WHO_HIM, "msg3", STATUS_SENT)
    val MESSAGE_4 = MessageModel(2, WHO_ME, "msg4", STATUS_NOT_SENT)
    val MESSAGE_5 = MessageModel(9, WHO_ME, "msg5", STATUS_SENT)

    val NOTIFICATION_1 = NotificationsModel(9, "msg1")
    val NOTIFICATION_2 = NotificationsModel(4, "msg2")
    val NOTIFICATION_3 = NotificationsModel(3, "msg3")
    val NOTIFICATION_4 = NotificationsModel(7, "msg4")
    val NOTIFICATION_5 = NotificationsModel(8, "msg5")

    private lateinit var appDataBase: AppDatabase
    private lateinit var SUT: ContactsDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDataBase = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()

        SUT = appDataBase.getContacsDao()
    }

    @Test
    fun getAllMessages_insertMany_assertContainsAll() = runBlocking {
        // arrange
        SUT.insertMesssage(MESSAGE_1)
        SUT.insertMesssage(MESSAGE_2)
        SUT.insertMesssage(MESSAGE_3)
        SUT.insertMesssage(MESSAGE_4)

        // act
        val list = SUT.getAllMessages()

        // assert
        assertEquals(list.size, 4)
        assertEquals(
            list.containsAll(
                listOf(
                    MESSAGE_1.copy(),
                    MESSAGE_2.copy(),
                    MESSAGE_3.copy(),
                    MESSAGE_4.copy()
                )
            ),
            true
        )

        Unit
    }

    @Test
    fun getAllMessages_insertMany_assertInDescendingOrder() = runBlocking {
        // arrange
        SUT.insertMesssage(MESSAGE_1)
        SUT.insertMesssage(MESSAGE_2)
        SUT.insertMesssage(MESSAGE_3)
        SUT.insertMesssage(MESSAGE_4)

        // act
        val list = SUT.getAllMessages()

        // assert
        val isDescending = (
                list[0].order > list[1].order &&
                        list[1].order > list[2].order &&
                        list[2].order > list[3].order
                )

        assertEquals(isDescending, true)
        Unit
    }

    @Test
    fun insertMessage_insertMany_assertContainsAll() = runBlocking {
        // arrange
        SUT.insertMesssage(MESSAGE_1)
        SUT.insertMesssage(MESSAGE_1.copy(msg="msg5"))
        SUT.insertMesssage(MESSAGE_3)
        SUT.insertMesssage(MESSAGE_1.copy(msg="msg10"))

        // act
        val list = SUT.getAllMessages()

        // assert
        assertEquals(list.size, 2)
        assert(list[0].order > list[1].order)
        assert(list[1].msg=="msg10")
        Unit
    }

    @Test
    fun updateMessage_insertSame_assertUpdated() = runBlocking {
        // arrange
        SUT.insertMesssage(MESSAGE_1)
        SUT.updateMessage(MESSAGE_1.copy(msg = "msg11"))

        // act
        val list = SUT.getAllMessages()

        // assert
        assertEquals(list.size, 1)
        assert(list[0].msg=="msg11")

        Unit
    }

    @Test
    fun updateMessage_updateUnexisting_assertInserted() = runBlocking {
        // arrange
        SUT.updateMessage(MESSAGE_1.copy(msg = "msg11"))

        // act
        val list = SUT.getAllMessages()

        // assert
        assertEquals(list.size, 1)
        assert(list[0].msg=="msg11")
        assertEquals(list[0].order, MESSAGE_1.order)

        Unit
    }


    @Test
    fun getAllNotifications_insertMany_assertContainsAll() = runBlocking {
        // arrange
        SUT.insertNotification(NOTIFICATION_1)
        SUT.insertNotification(NOTIFICATION_2)
        SUT.insertNotification(NOTIFICATION_3)
        SUT.insertNotification(NOTIFICATION_4)

        // act
        val list = SUT.getAllNotifications()

        // assert
        assertEquals(list.size, 4)
        assertEquals(
            list.containsAll(
                listOf(
                    NOTIFICATION_1.copy(),
                    NOTIFICATION_2.copy(),
                    NOTIFICATION_3.copy(),
                    NOTIFICATION_4.copy()
                )
            ),
            true
        )

        Unit
    }

    @Test
    fun getAllNotifications_insertNew_assertInDescendingOrder() = runBlocking {
        // arrange
        SUT.insertNotification(NOTIFICATION_1)
        SUT.insertNotification(NOTIFICATION_2)
        SUT.insertNotification(NOTIFICATION_3)
        SUT.insertNotification(NOTIFICATION_4)

        // act
        val list = SUT.getAllNotifications()

        // assert
        val isDescending = (
                list[0].order > list[1].order &&
                        list[1].order > list[2].order &&
                        list[2].order > list[3].order
                )

        assertEquals(isDescending, true)
        Unit
    }

    @After
    fun close() {
        appDataBase.close()
    }
}















