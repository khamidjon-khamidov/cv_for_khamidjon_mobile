package com.hamidjonhamidov.cvforkhamidjon.models.offline.main

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity(tableName = "achievements")
data class AchievementModel(

    @PrimaryKey
    @ColumnInfo(name = "listId")
    var listId: Int = 0,

    @ColumnInfo(name = "listTitle")
    var listTitle: String = "",

    @ColumnInfo(name = "hList")
    var honorsList: @RawValue List<Honor> = listOf()

) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AchievementModel

        if (listId != other.listId) return false
        if (listTitle != other.listTitle) return false
        if (honorsList != other.honorsList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = listId
        result = 31 * result + listTitle.hashCode()
        result = 31 * result + honorsList.hashCode()
        return result
    }

    override fun toString(): String {
        return "AchievementModel(listId=$listId, listTitle='$listTitle', honorsList=$honorsList)"
    }


}
data class Honor(
    var itemId: Int,
    var itemTitle: String,
    var itemDescription: String,
    var itemLink: String
)























