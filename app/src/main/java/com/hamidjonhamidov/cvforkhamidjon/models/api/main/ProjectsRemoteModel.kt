package com.hamidjonhamidov.cvforkhamidjon.models.api.main

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ProjectsRemoteModel(
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