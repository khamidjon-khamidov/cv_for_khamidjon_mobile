package com.hamidjonhamidov.whoiskhamidjon.models.about_me

import android.os.Parcelable
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "about_me")
class AboutMeModel(

    @SerializedName("pk")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk") var pk: Int = -1,

    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email") var email: String = "Could'nt load",

    @SerializedName("address")
    @Expose
    @ColumnInfo(name = "address") var address: String = "Could'nt load",

    @SerializedName("phone_number")
    @Expose
    @ColumnInfo(name = "phone_number") var phone_number: String = "Could'nt load",

    @SerializedName("tuit")
    @Expose
    @ColumnInfo(name = "tuit") var tuit: String = "Could'nt load",

    @SerializedName("south_bank")
    @Expose
    @ColumnInfo(name = "south_bank") var south_bank: String = "Couldn't load",

    @SerializedName("profile_image_url")
    @Expose
    @ColumnInfo(name = "profile_image_url") var profile_image_url: String = "Couldn't load"


) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as AboutMeModel

        if (pk == other.pk &&
            email == other.email &&
            address == other.address &&
            phone_number == other.phone_number &&
            tuit == other.tuit &&
            south_bank == other.south_bank &&
            profile_image_url == other.profile_image_url
        )
            return true

        return false
    }

    private fun convertUniStrToArrayList(someStr: String): ArrayList<String> {

        if (someStr.isEmpty()) return ArrayList()

        var str = someStr
        str = str.removeRange(0, 1)
        str = str.removeRange(str.length - 1, str.length)



        return str.trim().splitToSequence(',')
            .filter { it.isNotEmpty() }
            .toCollection(ArrayList())
    }

    fun convertStrToSubject(listInString: String): Subject {
        val list = convertUniStrToArrayList(listInString)
        var subjectOr = ""
        var scoreOr = ""

        var i = 0
        val TAG = "AppDebug"
        Log.d(TAG, "AboutMeModel: convertStrToSubject: item = $list")
        for (item in list) {
            Log.d(TAG, "AboutMeModel: convertStrToSubject: item = $item")
            val subject = item.substring(0, item.lastIndexOf("-")).trim()
            val score = item.substring(item.lastIndexOf("-") + 1, item.length).trim()

            i++
            subjectOr = "$subjectOr\n$i. $subject\n"
            scoreOr = "$scoreOr\n$score\n"

        }

        Log.d(TAG, "AboutMeModel: convertStrToSubject: subjectOr = $subjectOr")
        Log.d(TAG, "AboutMeModel: convertStrToSubject: scoreOr = $scoreOr")
        return Subject(subjectOr, scoreOr)
    }


    override fun toString(): String {
        return """
            pk=$pk, email=$email, address=$address, phoneNumber=$phone_number, tuit=$tuit, southBank=$south_bank, profile_image_url=$profile_image_url
        """.trimIndent()
    }

    override fun hashCode(): Int {
        var result = pk
        result = 31 * result + email.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + phone_number.hashCode()
        result = 31 * result + tuit.hashCode()
        result = 31 * result + south_bank.hashCode()
        result = 31 * result + profile_image_url.hashCode()
        return result
    }
}

data class Subject(var subject: String = "", var score: String = "")


// sun to phonebox
// boys to boys
// add another picture (star)




























