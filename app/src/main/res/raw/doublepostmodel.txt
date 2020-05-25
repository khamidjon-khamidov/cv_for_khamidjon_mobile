package com.hamidjonhamidov.cvforkhamidjon.util

import android.os.Parcelable
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.PostModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DoublePostModel(
    val postModel1: PostModel,
    val postModel2: PostModel?
): Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DoublePostModel

        if (postModel1 != other.postModel1) return false
        if (postModel2 != other.postModel2) return false

        return true
    }

    override fun hashCode(): Int {
        var result = postModel1.hashCode()
        result = 31 * result + (postModel2?.hashCode() ?: 0)
        return result
    }
}