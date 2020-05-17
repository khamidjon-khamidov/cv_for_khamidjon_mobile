package com.hamidjonhamidov.cvforkhamidjon.util.job_manager


class JobManager<JobType>{
    private val jobList: HashSet<String> = HashSet()

    fun isJobActive(job: JobType): Boolean {
        return jobList.contains(job.toString())
    }

    fun isJobActive(whichFragment: String): Boolean{

    }

    fun removeJob(job: JobType) {
        jobList.remove(job.toString())
    }

    fun addJob(newJob: JobType): Boolean{
        if(jobList.contains(newJob.toString())){
            return false
        } else {
            jobList.add(newJob.toString())
            return true
        }
    }

    fun clearJobs(){
        jobList.clear()
    }

    fun jobSize(): Int = jobList.size
}














