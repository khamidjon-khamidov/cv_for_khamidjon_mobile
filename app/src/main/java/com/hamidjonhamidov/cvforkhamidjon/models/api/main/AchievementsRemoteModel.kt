package com.hamidjonhamidov.cvforkhamidjon.models.api.main

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.Honor
import kotlinx.android.parcel.RawValue

data class AchievementsRemoteModel(
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