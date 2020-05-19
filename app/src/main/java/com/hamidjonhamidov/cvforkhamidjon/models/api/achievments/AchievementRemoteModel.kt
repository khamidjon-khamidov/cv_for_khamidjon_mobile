package com.hamidjonhamidov.cvforkhamidjon.models.api.achievments

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hamidjonhamidov.cvforkhamidjon.models.offline.achievements.AchievementModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.achievements.Honor
import kotlinx.android.parcel.RawValue

data class AchievementRemoteModel(
    @SerializedName("listId")
    @Expose
    var listId: Int = 0,

    @SerializedName("listTitle")
    @Expose
    var listTitle: String = "",

    @SerializedName("hList")
    @Expose
    var honorsList: @RawValue List<Honor> = listOf()
)

fun AchievementRemoteModel.convertToAchievmentModel() =
    AchievementModel(
        listId,
        listTitle,
        honorsList
    )