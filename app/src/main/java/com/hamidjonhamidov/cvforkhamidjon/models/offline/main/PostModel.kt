package com.hamidjonhamidov.cvforkhamidjon.models.offline.main

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "posts")
data class PostModel(

    @PrimaryKey
    var postId: Int = 0,

    @ColumnInfo(name = "postDescription")
    var postDescription: String = "",

    @ColumnInfo(name = "postLink")
    var postLink: String = ""

): Parcelable{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostModel

        if (postId != other.postId) return false
        if (postDescription != other.postDescription) return false
        if (postLink != other.postLink) return false

        return true
    }

    override fun hashCode(): Int {
        var result = postId
        result = 31 * result + postDescription.hashCode()
        result = 31 * result + postLink.hashCode()
        return result
    }
}