package com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main

import androidx.room.*
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.ProjectModel

@Dao
interface ProjectsDao {

    @Query("SELECT * FROM projects")
    suspend fun getAllProjects(): List<ProjectModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: ProjectModel)

    @Transaction
    open suspend fun insertMany(projectList: List<ProjectModel>) {
        projectList.forEach { insert(it) }
    }

    @Query("DELETE FROM projects")
    suspend fun deleteAll()

    @Transaction
    open suspend fun insertManyAndReplace(projects: List<ProjectModel>) {
        deleteAll()
        insertMany(projects)
    }

    @Transaction
    open suspend fun insertManyAndGet(projects: List<ProjectModel>): List<ProjectModel> {
        insertManyAndReplace(projects)
        return getAllProjects()
    }

}