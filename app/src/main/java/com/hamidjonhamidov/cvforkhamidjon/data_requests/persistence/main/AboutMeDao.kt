package com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main

import androidx.room.*
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeInfo

@Dao
interface AboutMeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(aboutMeInfo: AboutMeInfo)

    @Query("DELETE FROM about_me")
    suspend fun deleteAll()

    @Query("SELECT * FROM about_me")
    suspend fun getAboutMe(): List<AboutMeInfo>

    @Transaction
    open suspend fun replaceAboutMe(aboutMeInfo: AboutMeInfo){
        deleteAll()
        insert(aboutMeInfo)
    }

    @Transaction
    open suspend fun replaceAboutMeAndGet(aboutMeInfo: AboutMeInfo): List<AboutMeInfo>{
        deleteAll()
        insert(aboutMeInfo)
        return getAboutMe()
    }
}