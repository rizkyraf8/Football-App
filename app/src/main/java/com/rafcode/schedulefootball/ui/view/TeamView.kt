package com.rafcode.schedulefootball.ui.view

import com.rafcode.schedulefootball.api.response.Teams
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback

interface TeamView : ApiRepositoryCallback<Teams> {
    fun onShowLoadingTeam()
    fun onHideLoadingTeam()
}