package com.hamidjonhamidov.cvforkhamidjon.models.offline.main

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "skills")
data class SkillModel(

    @ColumnInfo(name = "id")
    var id: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "percentage")
    var percentage: Int = 0,

    @ColumnInfo(name = "list")
    var skillsList: List<String> = listOf()

) : Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var pk: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SkillModel

        if (id != other.id) return false
        if (name != other.name) return false
        if (percentage != other.percentage) return false
        if (skillsList != other.skillsList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + percentage
        result = 31 * result + skillsList.hashCode()
        return result
    }


}