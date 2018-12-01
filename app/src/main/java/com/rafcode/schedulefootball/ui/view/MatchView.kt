package com.rafcode.schedulefootball.ui.view

import com.rafcode.schedulefootball.api.response.Events
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback

interface MatchView : ApiRepositoryCallback<Events> {
    fun onShowLoadingMatch()
    fun onHideLoadingMatch()
}