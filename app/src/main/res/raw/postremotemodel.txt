package com.hamidjonhamidov.cvforkhamidjon.models.api.main

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.PostModel

data class PostRemoteModel(

    @SerializedName("postId")
    @Expose
    var postId: Int = 0,

    @SerializedName("postDescription")
    @Expose
    var postDescription: String = "",

    @SerializedName("postLink")
    @Expose
    var postLink: String = ""
)

fun PostRemoteModel.convertToPostModel() =
    PostModel(
        postId,
        postDescription,
        postLink
    )