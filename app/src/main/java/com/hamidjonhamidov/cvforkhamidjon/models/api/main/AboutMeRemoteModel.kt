package com.hamidjonhamidov.cvforkhamidjon.models.api.main

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.Education
import kotlinx.android.parcel.RawValue


data class AboutMeRemoteModel (
    @SerializedName("dateOfBirth")
    @Expose
    var dateOfBirth: String = "14 Sep 1998",

    @SerializedName("address")
    @Expose
    var address: String = "133B Granville Road, London",

    @SerializedName("email")
    @Expose
    var email: String = "hamidovhamid1998@mail.ru",

    @SerializedName("phone")
    @Expose
    var phone: String = "07565 336207",

    @SerializedName("education")
    @Expose
    var education: @RawValue List<Education> = listOf(),

    @SerializedName("pictureLink")
    @Expose
    var pictureLink: String = "Couldn't Load",

    @SerializedName("description")
    @Expose
    var description: String = "Couldn't Load"
)