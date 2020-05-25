package com.hamidjonhamidov.cvforkhamidjon.util.data_manager

import kotlin.reflect.KClass


class JobManager<JobType : Any>{
    private val jobList: HashSet<KClass<out JobType>> = HashSet()

    fun isJobActive(job: JobType): Boolean {
        return jobList.contains(job::class)
    }


    fun removeJob(job: JobType) {
        jobList.remove(job::class)
    }

    fun addJob(newJob: JobType): Boolean{
        if(jobList.contains(newJob::class)){
            return false
        } else {
            jobList.add(newJob::class)
            return true
        }
    }

    fun clearJobs(){
        jobList.clear()
    }

    fun jobSize(): Int = jobList.size
}














