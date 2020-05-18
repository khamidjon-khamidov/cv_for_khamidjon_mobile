package com.hamidjonhamidov.cvforkhamidjon.util.data_manager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewDestEvent
import com.hamidjonhamidov.cvforkhamidjon.util.Message
import com.hamidjonhamidov.cvforkhamidjon.util.UIType
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class InboxManagerTest{

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val TEST_MESSAGE1 = Message(
        "title 1",
        "des 1",
        UIType.Dialog(),
        true
    )

    val TEST_MESSAGE2 = Message(
        "title 2",
        "des 2",
        UIType.Dialog(),
        true
    )

    val TEST_MESSAGE3 = Message(
        "title 3",
        "des 3",
        UIType.Dialog(),
        true
    )

    val TEST_MESSAGE4 = Message(
        "title 4",
        "des 4",
        UIType.Dialog(),
        true
    )

    lateinit var aboutmeFragmentDest: MainViewDestEvent
    lateinit var mySkillsFragmentDest: MainViewDestEvent


    lateinit var SUT: InboxManager<MainViewDestEvent>

    @Before
    fun setUp() {

        aboutmeFragmentDest = MainViewDestEvent.AboutMeFragmentDest()
        mySkillsFragmentDest = MainViewDestEvent.MySkillsFragmentDest()

        SUT = InboxManager()
    }

    @Test
    fun receiveNewMessge_saveToInbox_assertSizeChange() {
        // arrange

        // act
        SUT.receiveNewMessage(aboutmeFragmentDest, TEST_MESSAGE1)
        SUT.receiveNewMessage(aboutmeFragmentDest, TEST_MESSAGE2)
        SUT.receiveNewMessage(mySkillsFragmentDest, TEST_MESSAGE3)
        SUT.receiveNewMessage(mySkillsFragmentDest, TEST_MESSAGE4)

        // assert
        assertEquals(SUT.getInboxSize(aboutmeFragmentDest), 2)
        assertEquals(SUT.getInboxSize(mySkillsFragmentDest), 2)
    }

    @Test
    fun receiveNewMessage_uiMessageNotifierNotified_assertCorrectMessage() =  runBlocking {
        // arrange
        var k = 0
        var p = 0
        val notifierAboutME =
            SUT.getMessagesNotifer(aboutmeFragmentDest)

        notifierAboutME.observeForever{
            k = 1
        }

        val notifierSkills =
            SUT.getMessagesNotifer(mySkillsFragmentDest)

        notifierSkills.observeForever{
            p = 2
        }
        // act
            SUT.receiveNewMessage(aboutmeFragmentDest, TEST_MESSAGE1)
            SUT.receiveNewMessage(mySkillsFragmentDest, TEST_MESSAGE2)
            delay(1000)


        // assert
        assertEquals(k, 1)
        assertEquals(p, 2)
        assertEquals(notifierAboutME.value, UIMessage(TEST_MESSAGE1))
        assertEquals(notifierSkills.value, UIMessage(TEST_MESSAGE2))
        Unit
    }

    @Test
    fun receiveNewMessge_assertProgressBarNotNotified() = runBlocking {
        // arrange
        var k = 0
        val progressBarNotifer =
            SUT.getProgressBarNotifier(aboutmeFragmentDest)
        progressBarNotifer.observeForever{
            k = 1
        }

        // act
        SUT.receiveNewMessage(aboutmeFragmentDest, TEST_MESSAGE1)

        // assert
        assertEquals(k, 0)
    }

    @Test
    fun setMessageInInboxToProcess_setMessageToProcess_returnTrue() {
        // arrange
        SUT.receiveNewMessage(aboutmeFragmentDest, TEST_MESSAGE1)
        SUT.receiveNewMessage(aboutmeFragmentDest, TEST_MESSAGE2)
        SUT.receiveNewMessage(aboutmeFragmentDest, TEST_MESSAGE3)

        // act
        SUT.setMessageInInboxToProcess(aboutmeFragmentDest)

        // assert
        assertEquals(SUT.isMessageInInboxInProcess(aboutmeFragmentDest), true)
    }

    @Test
    fun removeMessageFromInbox_checkCorrectRemoved() {
        // arrange
        SUT.receiveNewMessage(aboutmeFragmentDest, TEST_MESSAGE1)
        SUT.receiveNewMessage(aboutmeFragmentDest, TEST_MESSAGE2)
        SUT.receiveNewMessage(aboutmeFragmentDest, TEST_MESSAGE3)
        SUT.receiveNewMessage(aboutmeFragmentDest, TEST_MESSAGE4)

        // act
        SUT.removeMessageFromInbox(aboutmeFragmentDest)
        SUT.removeMessageFromInbox(aboutmeFragmentDest)
        SUT.removeMessageFromInbox(aboutmeFragmentDest)

        // assert
        assertEquals(SUT.getMessageFromInbox(aboutmeFragmentDest), UIMessage(TEST_MESSAGE4))
        assertNotEquals(SUT.getMessageFromInbox(aboutmeFragmentDest), UIMessage(TEST_MESSAGE1))
    }

    @Test
    fun getMessageFromInbox_() {
        // arrange
        SUT.receiveNewMessage(aboutmeFragmentDest, TEST_MESSAGE1)
        SUT.receiveNewMessage(aboutmeFragmentDest, TEST_MESSAGE2)
        SUT.receiveNewMessage(mySkillsFragmentDest, TEST_MESSAGE3)
        SUT.receiveNewMessage(mySkillsFragmentDest, TEST_MESSAGE4)

        // act
        SUT.removeMessageFromInbox(aboutmeFragmentDest)
        SUT.removeMessageFromInbox(mySkillsFragmentDest)

        // assert
        assertEquals(SUT.getMessageFromInbox(aboutmeFragmentDest), UIMessage(TEST_MESSAGE2))
        assertEquals(SUT.getMessageFromInbox(mySkillsFragmentDest), UIMessage(TEST_MESSAGE4))
    }
}





























