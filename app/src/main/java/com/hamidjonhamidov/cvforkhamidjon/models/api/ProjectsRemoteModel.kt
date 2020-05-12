package com.hamidjonhamidov.cvforkhamidjon.models.api

import com.google.gson.annotations.SerializedName


data class ProjectsRemoteModel(
    @SerializedName("projectId")
    var projectId: Int = 0,

    @SerializedName("projectTime")
    var projectTime: String = ".......",

    @SerializedName("projectTitle")
    var projectTitle: String = ".......",

    @SerializedName("projectDes")
    var projectDes: String = "........",

    @SerializedName("projectLink")
    var projectLink: String = ".......",

    @SerializedName("projectGitLink")
    var projectGitLink: String = "......"
)