package com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.contact

import androidx.room.*
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.NotificationsModel

@Dao
interface ContactsDao {

    @Query("SELECT * FROM messages ORDER by `order` DESC")
    suspend fun getAllMessages(): List<MessageModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMesssage(message: MessageModel)

    @Update
    suspend fun updateMessage(message: MessageModel)

    @Query("SELECT * FROM notifications ORDER by `order` DESC")
    suspend fun getAllNotifications(): List<NotificationsModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notificationsModel: NotificationsModel)
}















