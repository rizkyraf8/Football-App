package com.rafcode.schedulefootball.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Leagues {

    @SerializedName("leagues")
    @Expose
    var leagues: List<League>? = null
}
