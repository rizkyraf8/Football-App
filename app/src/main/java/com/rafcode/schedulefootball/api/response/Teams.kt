package com.rafcode.schedulefootball.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Teams {
    @SerializedName("teams")
    @Expose
    var teams: List<Team>? = null
}
