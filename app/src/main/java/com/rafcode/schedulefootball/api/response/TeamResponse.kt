package com.rafcode.schedulefootball.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TeamResponse {
    @SerializedName("teams")
    @Expose
    var teams: List<Team>? = null
}
