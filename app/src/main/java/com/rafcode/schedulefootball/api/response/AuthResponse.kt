package com.rafcode.schedulefootball.api.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.rafcode.schedulefootball.api.CommonResponse

class AuthResponse() : CommonResponse(), Parcelable {
    @SerializedName("data")
    @Expose
    var data: UserBeans? = null

    constructor(parcel: Parcel) : this() {
        data = parcel.readParcelable(UserBeans::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuthResponse> {
        override fun createFromParcel(parcel: Parcel): AuthResponse {
            return AuthResponse(parcel)
        }

        override fun newArray(size: Int): Array<AuthResponse?> {
            return arrayOfNulls(size)
        }
    }
}

class UserBeans() : Parcelable {
    @SerializedName("userId")
    @Expose
    var userId: String? = null

    @SerializedName("userEmail")
    @Expose
    var userEmail: String? = null

    @SerializedName("userName")
    @Expose
    var userName: String? = null

    @SerializedName("userPassword")
    @Expose
    var userPassword: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    constructor(parcel: Parcel) : this() {
        userId = parcel.readString()
        userEmail = parcel.readString()
        userName = parcel.readString()
        userPassword = parcel.readString()
        createdAt = parcel.readString()
        token = parcel.readString()
        type = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(userEmail)
        parcel.writeString(userName)
        parcel.writeString(userPassword)
        parcel.writeString(createdAt)
        parcel.writeString(token)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserBeans> {
        override fun createFromParcel(parcel: Parcel): UserBeans {
            return UserBeans(parcel)
        }

        override fun newArray(size: Int): Array<UserBeans?> {
            return arrayOfNulls(size)
        }
    }

}