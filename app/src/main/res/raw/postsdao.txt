package com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.main

import androidx.room.*
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.PostModel

@Dao
interface PostsDao {
    @Query("SELECT * FROM posts")
    suspend fun getAllPosts(): List<PostModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostModel)

    @Transaction
    open suspend fun insertMany(postList: List<PostModel>) {
        postList.forEach { insert(it) }
    }

    @Query("DELETE FROM posts")
    suspend fun deleteAll()

    @Transaction
    open suspend fun insertManyAndReplace(posts: List<PostModel>) {
        deleteAll()
        insertMany(posts)
    }

    @Transaction
    open suspend fun insertManyAndGet(posts: List<PostModel>): List<PostModel> {
        insertManyAndReplace(posts)
        return getAllPosts()
    }

}