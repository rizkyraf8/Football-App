package com.rafcode.schedulefootball.api.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthRequest() : Parcelable {

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    constructor(parcel: Parcel) : this() {
        email = parcel.readString()
        password = parcel.readString()
        name = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuthRequest> {
        override fun createFromParcel(parcel: Parcel): AuthRequest {
            return AuthRequest(parcel)
        }

        override fun newArray(size: Int): Array<AuthRequest?> {
            return arrayOfNulls(size)
        }
    }
}