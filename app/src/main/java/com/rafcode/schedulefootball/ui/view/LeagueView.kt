package com.rafcode.schedulefootball.ui.view

import com.rafcode.schedulefootball.api.response.Events
import com.rafcode.schedulefootball.api.response.Leagues
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback

interface LeagueView : ApiRepositoryCallback<Leagues> {
    fun onShowLoadingLeague()
    fun onHideLoadingLeague()
}