package com.rafcode.schedulefootball.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EventResponse {

    @SerializedName("events")
    @Expose
    var events: List<Event>? = null

}