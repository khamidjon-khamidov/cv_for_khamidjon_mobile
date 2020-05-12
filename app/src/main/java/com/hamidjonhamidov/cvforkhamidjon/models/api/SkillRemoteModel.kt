package com.hamidjonhamidov.cvforkhamidjon.models.api

import com.google.gson.annotations.SerializedName

data class SkillRemoteModel(

    @SerializedName("id")
    var id: String = "",

    @SerializedName("name")
    var name: String = "",

    @SerializedName("percentage")
    var percentage: Int = 0,

    @SerializedName("list")
    var skillsList: List<String> = listOf()
)