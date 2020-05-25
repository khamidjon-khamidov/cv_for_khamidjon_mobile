package com.hamidjonhamidov.cvforkhamidjon.models.api.main

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.ProjectModel


data class ProjectRemoteModel(
    @SerializedName("projectId")
    @Expose
    var projectId: Int = 0,

    @SerializedName("projectTime")
    @Expose
    var projectTime: String = ".......",

    @SerializedName("projectTitle")
    @Expose
    var projectTitle: String = ".......",

    @SerializedName("projectDes")
    @Expose
    var projectDes: String = "........",

    @SerializedName("projectLink")
    @Expose
    var projectLink: String = ".......",

    @SerializedName("projectGitLink")
    @Expose
    var projectGitLink: String = "......"
)

fun ProjectRemoteModel.convertToProjectModel() =
    ProjectModel(
        projectId,
        projectTime,
        projectTitle,
        projectDes,
        projectLink,
        projectGitLink
    )








