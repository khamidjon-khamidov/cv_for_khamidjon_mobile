package com.hamidjonhamidov.cvforkhamidjon.models.api

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.Honor
import kotlinx.android.parcel.RawValue

data class AchievementsRemoteModel(
    @SerializedName("listId")
    var listId: Int = 0,

    @SerializedName("listTitle")
    var listTitle: String = "",

    @SerializedName("hList")
    var honorsList: @RawValue List<Honor> = listOf()
)