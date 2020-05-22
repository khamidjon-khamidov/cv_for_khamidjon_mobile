package com.hamidjonhamidov.cvforkhamidjon.models.offline.contact

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "notifications")
data class NotificationsModel(
    @PrimaryKey
    var order: Int = 0,

    var message: String = ""

) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NotificationsModel

        if (order != other.order) return false
        if (message != other.message) return false

        return true
    }

    override fun hashCode(): Int {
        var result = order
        result = 31 * result + message.hashCode()
        return result
    }
}