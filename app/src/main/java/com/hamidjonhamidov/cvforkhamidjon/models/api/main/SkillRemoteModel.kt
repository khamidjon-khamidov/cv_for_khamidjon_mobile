package com.hamidjonhamidov.cvforkhamidjon.models.api.main

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.SkillModel

data class SkillRemoteModel(

    @SerializedName("id")
    @Expose
    var id: String = "",

    @SerializedName("name")
    @Expose
    var name: String = "",

    @SerializedName("percentage")
    @Expose
    var percentage: Int = 0,

    @SerializedName("list")
    @Expose
    var skillsList: List<String> = listOf()
)

fun SkillRemoteModel.convertToSkillModel() =
    SkillModel(id, name, percentage, skillsList)