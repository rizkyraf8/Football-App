package com.rafcode.schedulefootball.api.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Team() : Parcelable {
    @SerializedName("idTeam")
    @Expose
    var idTeam: String? = null

    @SerializedName("idSoccerXML")
    @Expose
    var idSoccerXML: String? = null

    @SerializedName("intLoved")
    @Expose
    var intLoved: String? = null

    @SerializedName("strTeam")
    @Expose
    var strTeam: String? = null

    @SerializedName("strTeamShort")
    @Expose
    var strTeamShort: String? = null

    @SerializedName("strAlternate")
    @Expose
    var strAlternate: String? = null

    @SerializedName("intFormedYear")
    @Expose
    var intFormedYear: String? = null

    @SerializedName("strSport")
    @Expose
    var strSport: String? = null

    @SerializedName("strLeague")
    @Expose
    var strLeague: String? = null

    @SerializedName("idLeague")
    @Expose
    var idLeague: String? = null

    @SerializedName("strDivision")
    @Expose
    var strDivision: String? = null

    @SerializedName("strManager")
    @Expose
    var strManager: String? = null

    @SerializedName("strStadium")
    @Expose
    var strStadium: String? = null

    @SerializedName("strKeywords")
    @Expose
    var strKeywords: String? = null

    @SerializedName("strRSS")
    @Expose
    var strRSS: String? = null

    @SerializedName("strStadiumThumb")
    @Expose
    var strStadiumThumb: String? = null

    @SerializedName("strStadiumDescription")
    @Expose
    var strStadiumDescription: String? = null

    @SerializedName("strStadiumLocation")
    @Expose
    var strStadiumLocation: String? = null

    @SerializedName("intStadiumCapacity")
    @Expose
    var intStadiumCapacity: String? = null

    @SerializedName("strWebsite")
    @Expose
    var strWebsite: String? = null

    @SerializedName("strFacebook")
    @Expose
    var strFacebook: String? = null

    @SerializedName("strTwitter")
    @Expose
    var strTwitter: String? = null

    @SerializedName("strInstagram")
    @Expose
    var strInstagram: String? = null

    @SerializedName("strDescriptionEN")
    @Expose
    var strDescriptionEN: String? = null

    @SerializedName("strDescriptionDE")
    @Expose
    var strDescriptionDE: String? = null

    @SerializedName("strDescriptionFR")
    @Expose
    var strDescriptionFR: String? = null

    @SerializedName("strDescriptionCN")
    @Expose
    var strDescriptionCN: String? = null

    @SerializedName("strDescriptionIT")
    @Expose
    var strDescriptionIT: String? = null

    @SerializedName("strDescriptionJP")
    @Expose
    var strDescriptionJP: String? = null

    @SerializedName("strDescriptionRU")
    @Expose
    var strDescriptionRU: String? = null

    @SerializedName("strDescriptionES")
    @Expose
    var strDescriptionES: String? = null

    @SerializedName("strDescriptionPT")
    @Expose
    var strDescriptionPT: String? = null

    @SerializedName("strDescriptionSE")
    @Expose
    var strDescriptionSE: String? = null

    @SerializedName("strDescriptionNL")
    @Expose
    var strDescriptionNL: String? = null

    @SerializedName("strDescriptionHU")
    @Expose
    var strDescriptionHU: String? = null

    @SerializedName("strDescriptionNO")
    @Expose
    var strDescriptionNO: String? = null

    @SerializedName("strDescriptionIL")
    @Expose
    var strDescriptionIL: String? = null

    @SerializedName("strDescriptionPL")
    @Expose
    var strDescriptionPL: String? = null

    @SerializedName("strGender")
    @Expose
    var strGender: String? = null

    @SerializedName("strCountry")
    @Expose
    var strCountry: String? = null

    @SerializedName("strTeamBadge")
    @Expose
    var strTeamBadge: String? = null

    @SerializedName("strTeamJersey")
    @Expose
    var strTeamJersey: String? = null

    @SerializedName("strTeamLogo")
    @Expose
    var strTeamLogo: String? = null

    @SerializedName("strTeamFanart1")
    @Expose
    var strTeamFanart1: String? = null

    @SerializedName("strTeamFanart2")
    @Expose
    var strTeamFanart2: String? = null

    @SerializedName("strTeamFanart3")
    @Expose
    var strTeamFanart3: String? = null

    @SerializedName("strTeamFanart4")
    @Expose
    var strTeamFanart4: String? = null

    @SerializedName("strTeamBanner")
    @Expose
    var strTeamBanner: String? = null

    @SerializedName("strYoutube")
    @Expose
    var strYoutube: String? = null

    @SerializedName("strLocked")
    @Expose
    var strLocked: String? = null

    constructor(parcel: Parcel) : this() {
        idTeam = parcel.readString()
        idSoccerXML = parcel.readString()
        intLoved = parcel.readString()
        strTeam = parcel.readString()
        strTeamShort = parcel.readString()
        strAlternate = parcel.readString()
        intFormedYear = parcel.readString()
        strSport = parcel.readString()
        strLeague = parcel.readString()
        idLeague = parcel.readString()
        strDivision = parcel.readString()
        strManager = parcel.readString()
        strStadium = parcel.readString()
        strKeywords = parcel.readString()
        strRSS = parcel.readString()
        strStadiumThumb = parcel.readString()
        strStadiumDescription = parcel.readString()
        strStadiumLocation = parcel.readString()
        intStadiumCapacity = parcel.readString()
        strWebsite = parcel.readString()
        strFacebook = parcel.readString()
        strTwitter = parcel.readString()
        strInstagram = parcel.readString()
        strDescriptionEN = parcel.readString()
        strDescriptionDE = parcel.readString()
        strDescriptionFR = parcel.readString()
        strDescriptionCN = parcel.readString()
        strDescriptionIT = parcel.readString()
        strDescriptionJP = parcel.readString()
        strDescriptionRU = parcel.readString()
        strDescriptionES = parcel.readString()
        strDescriptionPT = parcel.readString()
        strDescriptionSE = parcel.readString()
        strDescriptionNL = parcel.readString()
        strDescriptionHU = parcel.readString()
        strDescriptionNO = parcel.readString()
        strDescriptionIL = parcel.readString()
        strDescriptionPL = parcel.readString()
        strGender = parcel.readString()
        strCountry = parcel.readString()
        strTeamBadge = parcel.readString()
        strTeamJersey = parcel.readString()
        strTeamLogo = parcel.readString()
        strTeamFanart1 = parcel.readString()
        strTeamFanart2 = parcel.readString()
        strTeamFanart3 = parcel.readString()
        strTeamFanart4 = parcel.readString()
        strTeamBanner = parcel.readString()
        strYoutube = parcel.readString()
        strLocked = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idTeam)
        parcel.writeString(idSoccerXML)
        parcel.writeString(intLoved)
        parcel.writeString(strTeam)
        parcel.writeString(strTeamShort)
        parcel.writeString(strAlternate)
        parcel.writeString(intFormedYear)
        parcel.writeString(strSport)
        parcel.writeString(strLeague)
        parcel.writeString(idLeague)
        parcel.writeString(strDivision)
        parcel.writeString(strManager)
        parcel.writeString(strStadium)
        parcel.writeString(strKeywords)
        parcel.writeString(strRSS)
        parcel.writeString(strStadiumThumb)
        parcel.writeString(strStadiumDescription)
        parcel.writeString(strStadiumLocation)
        parcel.writeString(intStadiumCapacity)
        parcel.writeString(strWebsite)
        parcel.writeString(strFacebook)
        parcel.writeString(strTwitter)
        parcel.writeString(strInstagram)
        parcel.writeString(strDescriptionEN)
        parcel.writeString(strDescriptionDE)
        parcel.writeString(strDescriptionFR)
        parcel.writeString(strDescriptionCN)
        parcel.writeString(strDescriptionIT)
        parcel.writeString(strDescriptionJP)
        parcel.writeString(strDescriptionRU)
        parcel.writeString(strDescriptionES)
        parcel.writeString(strDescriptionPT)
        parcel.writeString(strDescriptionSE)
        parcel.writeString(strDescriptionNL)
        parcel.writeString(strDescriptionHU)
        parcel.writeString(strDescriptionNO)
        parcel.writeString(strDescriptionIL)
        parcel.writeString(strDescriptionPL)
        parcel.writeString(strGender)
        parcel.writeString(strCountry)
        parcel.writeString(strTeamBadge)
        parcel.writeString(strTeamJersey)
        parcel.writeString(strTeamLogo)
        parcel.writeString(strTeamFanart1)
        parcel.writeString(strTeamFanart2)
        parcel.writeString(strTeamFanart3)
        parcel.writeString(strTeamFanart4)
        parcel.writeString(strTeamBanner)
        parcel.writeString(strYoutube)
        parcel.writeString(strLocked)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Team> {
        override fun createFromParcel(parcel: Parcel): Team {
            return Team(parcel)
        }

        override fun newArray(size: Int): Array<Team?> {
            return arrayOfNulls(size)
        }
    }
}
