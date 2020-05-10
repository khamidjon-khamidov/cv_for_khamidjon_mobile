package com.hamidjonhamidov.cvforkhamidjon.models.offline

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "about_me")
data class AboutMeInfo(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk") var pk: Int = -1,

    @ColumnInfo(name = "dateOfBirth") var dateOfBirth: String = "Couldn't Load",

    @ColumnInfo(name = "address") var address: String = "Couldn't Load",

    @ColumnInfo(name = "email") var email: String = "Couldn't Load",

    @ColumnInfo(name = "phone") var phone: String = "Couldn't Load",

    @ColumnInfo(name = "pictureLinke") var pictureLinke: String = "Couldn't Load",

    @ColumnInfo(name = "description") var description: String = "Couldn't Load"

) : Parcelable {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AboutMeInfo

        if (pk != other.pk) return false
        if (dateOfBirth != other.dateOfBirth) return false
        if (address != other.address) return false
        if (email != other.email) return false
        if (phone != other.phone) return false
        if (pictureLinke != other.pictureLinke) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pk
        result = 31 * result + dateOfBirth.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + pictureLinke.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }

    override fun toString() =
        "AboutMeInfo(pk=$pk, dateOfBirth='$dateOfBirth', address='$address', email='$email', phone='$phone', pictureLinke='$pictureLinke', description='$description')"



}



















