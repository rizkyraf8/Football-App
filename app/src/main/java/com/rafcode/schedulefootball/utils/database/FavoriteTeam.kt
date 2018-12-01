package com.rafcode.schedulefootball.utils.database

import com.google.gson.Gson

class FavoriteTeam(val idEvent: Int, val gson: String) {

    companion object {
        const val TABLE_TEAM: String = "TABLE_TEAM"

        const val idTeam: String = "idTeam"
        const val gson: String = "gson"
    }

}
