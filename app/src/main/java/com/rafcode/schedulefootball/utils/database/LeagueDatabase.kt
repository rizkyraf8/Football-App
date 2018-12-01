package com.rafcode.schedulefootball.utils.database

import com.google.gson.Gson

class LeagueDatabase(val idLeague: Int, val strLeague: String, val strSport: String, val strLeagueAlternate: String) {

    companion object {
        const val TABLE_LEAGUE: String = "TABLE_LEAGUE"

        const val idLeague: String = "idLeague"
        const val strLeague: String = "strLeague"
        const val strSport: String = "strSport"
        const val strLeagueAlternate: String = "strLeagueAlternate"
    }

}
