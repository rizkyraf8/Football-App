package com.rafcode.schedulefootball.api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

open class CommonResponse() : Parcelable {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("error")
    @Expose
    var error: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    constructor(parcel: Parcel) : this() {
        status = parcel.readValue(Int::class.java.classLoader) as? Int
        error = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        message = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(status)
        parcel.writeValue(error)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommonResponse> {
        override fun createFromParcel(parcel: Parcel): CommonResponse {
            return CommonResponse(parcel)
        }

        override fun newArray(size: Int): Array<CommonResponse?> {
            return arrayOfNulls(size)
        }
    }
}