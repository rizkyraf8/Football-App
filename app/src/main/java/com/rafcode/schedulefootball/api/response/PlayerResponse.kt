package com.rafcode.schedulefootball.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PlayerResponse {

    @SerializedName("player")
    @Expose
    var player: List<Player>? = null

}