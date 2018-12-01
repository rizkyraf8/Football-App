package com.rafcode.schedulefootball.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.rafcode.schedulefootball.api.response.Event

class Events {

    @SerializedName("events")
    @Expose
    var events: List<Event>? = null

}