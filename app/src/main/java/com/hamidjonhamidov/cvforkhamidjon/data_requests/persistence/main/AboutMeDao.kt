package com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main

import androidx.room.*
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel

@Dao
interface AboutMeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(aboutMeModel: AboutMeModel)

    @Query("DELETE FROM about_me")
    suspend fun deleteAll()

    @Query("SELECT * FROM about_me")
    suspend fun getAboutMe(): List<AboutMeModel>

    @Transaction
    open suspend fun replaceAboutMe(aboutMeModel: AboutMeModel){
        deleteAll()
        insert(aboutMeModel)
    }

    @Transaction
    open suspend fun replaceAboutMeAndGet(aboutMeModel: AboutMeModel): List<AboutMeModel>{
        deleteAll()
        insert(aboutMeModel)
        return getAboutMe()
    }
}