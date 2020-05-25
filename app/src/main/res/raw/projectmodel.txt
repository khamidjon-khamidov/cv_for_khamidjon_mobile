package com.hamidjonhamidov.cvforkhamidjon.models.offline.main

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "projects")
data class ProjectModel(

    @PrimaryKey
    var projectId: Int = 0,

    @ColumnInfo(name="projectTime")
    var projectTime: String = ".......",

    @ColumnInfo(name="projectTitle")
    var projectTitle: String = ".......",

    @ColumnInfo(name="projectDes")
    var projectDes: String = "........",

    @ColumnInfo(name="projectLink")
    var projectLink: String = ".......",

    @ColumnInfo(name="projectGitLink")
    var projectGitLink: String = "......"
): Parcelable{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProjectModel

        if (projectId != other.projectId) return false
        if (projectTime != other.projectTime) return false
        if (projectTitle != other.projectTitle) return false
        if (projectDes != other.projectDes) return false
        if (projectLink != other.projectLink) return false
        if (projectGitLink != other.projectGitLink) return false

        return true
    }

    override fun hashCode(): Int {
        var result = projectId
        result = 31 * result + projectTime.hashCode()
        result = 31 * result + projectTitle.hashCode()
        result = 31 * result + projectDes.hashCode()
        result = 31 * result + projectLink.hashCode()
        result = 31 * result + projectGitLink.hashCode()
        return result
    }
}
