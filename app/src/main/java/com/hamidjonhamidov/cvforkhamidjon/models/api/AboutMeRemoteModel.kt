package com.hamidjonhamidov.cvforkhamidjon.models.api

import com.google.gson.annotations.SerializedName
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.Education
import kotlinx.android.parcel.RawValue


data class AboutMeRemoteModel (
    @SerializedName("dateOfBirth")
    var dateOfBirth: String = "14 Sep 1998",

    @SerializedName("address")
    var address: String = "133B Granville Road, London",

    @SerializedName("email")
    var email: String = "hamidovhamid1998@mail.ru",

    @SerializedName("phone")
    var phone: String = "07565 336207",

    @SerializedName("education")
    var education: @RawValue List<Education> = listOf(),

    @SerializedName("pictureLinke")
    var pictureLink: String = "Couldn't Load",

    @SerializedName("description")
    var description: String = "Couldn't Load"

)