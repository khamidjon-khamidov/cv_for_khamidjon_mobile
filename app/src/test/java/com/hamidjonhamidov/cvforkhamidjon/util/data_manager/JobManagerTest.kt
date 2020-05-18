package com.hamidjonhamidov.cvforkhamidjon.util.data_manager

import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainJobsEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainJobsEvent.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class JobManagerTest{

    lateinit var SUT: JobManager<MainJobsEvent>

    @Before
    fun setUp() {
        SUT = JobManager()
    }

    // add job -> new jobs, check job is active
    @Test
    fun addJob_addNewJobs_checkIfActive() {
        // arrange

        // act
        SUT.addJob(GetAboutMe())
        SUT.addJob(GetAchievements())

        // assert
        assertEquals(SUT.isJobActive(GetAchievements()), true)
        assertEquals(SUT.isJobActive(GetAboutMe()), true)

    }

    // add job -> 2 same type of jobs, second adding is false
    @Test
    fun addJob_addSameJobs_returnFalseWhenAdded2nd() {
        // arrange
        SUT.addJob(GetMySkills())
        SUT.addJob(GetProjects())

        // assert
        assertEquals(SUT.addJob(GetMySkills()), false)
    }

    // add new job, check if it is active
    @Test
    fun addJob_checkIfActive() {
        // arrange
        SUT.addJob(GetMySkills())
        SUT.addJob(GetProjects())

        // assert
        assertEquals(SUT.isJobActive(GetMySkills()), true)
    }

    // is JobActive -> check unexisting jobs
    @Test
    fun isJobActive_addJobCheckUnexistingJob_assertReturnFalse() {
        // arrange
        SUT.addJob(GetMySkills())
        SUT.addJob(GetProjects())

        // assert
        assertEquals(SUT.isJobActive(GetAboutMe()), false)
    }

    // isJobActive -> add existing job, check if it active
    @Test
    fun isJobActive_addSameJobsCheckIfActive_assertReturnsTrue() {
        // arrange
        SUT.addJob(GetMySkills())
        SUT.addJob(GetMySkills())
        SUT.addJob(GetProjects())
        SUT.addJob(GetMySkills())

        // assert
        assertEquals(SUT.isJobActive(GetMySkills()), true)
    }

    // remove job -> size 0, then check if size 0
    @Test
    fun removeJob_size0RemoveJob_returnSize0() {
        // arrange
        SUT.removeJob(GetAboutMe())

        // assert
        assertEquals(SUT.jobSize(), 0)
    }

    // remove job -> size 1,remove unexisting job then size 1, check if it is active
    @Test
    fun removeJob_addSameJobsCheckIfActive_assertReturnsTrue() {
        // arrange
        SUT.addJob(GetMySkills())
        SUT.addJob(GetProjects())
        SUT.removeJob(GetAboutMe())

        // assert
        assertEquals(SUT.jobSize(), 2)
        assertEquals(SUT.isJobActive(GetMySkills()), true)
        assertEquals(SUT.isJobActive(GetProjects()), true)
    }


    // clear job -> check if jobs are cleared
    @Test
    fun clearJob_addSameJobsClear_assertSizeZero() {
        // arrange
        SUT.addJob(GetMySkills())
        SUT.addJob(GetProjects())
        SUT.clearJobs()

        // assert
        assertEquals(SUT.jobSize(), 0)
    }

    // job size -> add new jobs, check job size
    @Test
    fun jobSize_addSameJobs_assertSize() {
        // arrange
        SUT.addJob(GetMySkills())
        SUT.addJob(GetProjects())

        // assert
        assertEquals(SUT.jobSize(), 2)
    }

}






















