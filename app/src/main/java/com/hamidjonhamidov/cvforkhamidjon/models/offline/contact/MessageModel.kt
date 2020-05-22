package com.hamidjonhamidov.cvforkhamidjon.models.offline.contact

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "messages")
data class MessageModel(

    @PrimaryKey
    var order: Int = 0,

    var toWhom: Boolean = WHO_HIM,

    var msg: String = "",

    var status: Boolean = STATUS_NOT_SENT

): Parcelable{

    companion object{
        val STATUS_SENT = true
        val STATUS_NOT_SENT = false
        val WHO_ME = false
        val WHO_HIM = true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MessageModel

        if (order != other.order) return false
        if (toWhom != other.toWhom) return false
        if (msg != other.msg) return false
        if (status != other.status) return false

        return true
    }

    override fun hashCode(): Int {
        var result = order
        result = 31 * result + toWhom.hashCode()
        result = 31 * result + msg.hashCode()
        result = 31 * result + status.hashCode()
        return result
    }


}















