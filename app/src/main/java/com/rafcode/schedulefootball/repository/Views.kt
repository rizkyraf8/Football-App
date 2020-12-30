package com.rafcode.schedulefootball.repository

import com.rafcode.schedulefootball.api.response.*

interface LeagueView : ApiRepositoryCallback<LeagueResponse> {
    fun onShowLoadingLeague()
    fun onHideLoadingLeague()
}

interface MatchSeacrhView : ApiRepositoryCallback<EventsSearch> {
    fun onShowLoadingMatch()
    fun onHideLoadingMatch()
}

interface MatchView : ApiRepositoryCallback<EventResponse> {
    fun onShowLoadingMatch()
    fun onHideLoadingMatch()
}

interface PlayerView : ApiRepositoryCallback<PlayerResponse> {
    fun onShowLoadingPlayer()
    fun onHideLoadingPlayer()
}

interface TeamView : ApiRepositoryCallback<TeamResponse> {
    fun onShowLoadingTeam()
    fun onHideLoadingTeam()
}

interface AuthView : ApiRepositoryCallback<AuthResponse> {
    fun onShowLoadingAuth()
    fun onHideLoadingAuth()
}