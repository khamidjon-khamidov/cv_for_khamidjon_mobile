package com.hamidjonhamidov.cvforkhamidjon.models.offline.main

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity(tableName = "about_me")
data class AboutMeInfo(

    @ColumnInfo(name = "dateOfBirth") var dateOfBirth: String = "14 Sep 1998",

    @ColumnInfo(name = "address") var address: String = "133B Granville Road, London",

    @ColumnInfo(name = "email") var email: String = "hamidovhamid1998@mail.ru",

    @ColumnInfo(name = "phone") var phone: String = "07565 336207",

    @ColumnInfo(name = "education") var education: @RawValue List<Education> = listOf(),

    @ColumnInfo(name = "pictureLinke") var pictureLink: String = "Couldn't Load",

    @ColumnInfo(name = "description") var description: String = "Couldn't Load"

) : Parcelable {

    @IgnoredOnParcel
    @PrimaryKey
    @ColumnInfo(name = "pk") var pk: Int = 1



    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AboutMeInfo

        if (dateOfBirth != other.dateOfBirth) return false
        if (address != other.address) return false
        if (email != other.email) return false
        if (phone != other.phone) return false
        if (education != other.education) return false
        if (pictureLink != other.pictureLink) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dateOfBirth.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + education.hashCode()
        result = 31 * result + pictureLink.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }

    override fun toString(): String {
        return "AboutMeInfo(dateOfBirth='$dateOfBirth', address='$address', email='$email', phone='$phone', education=$education, pictureLink='$pictureLink', description='$description')"
    }


}
data class Education(
    val name: String,
    val link: String
)


















