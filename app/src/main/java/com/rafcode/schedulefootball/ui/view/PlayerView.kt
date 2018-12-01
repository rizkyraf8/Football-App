package com.rafcode.schedulefootball.ui.view

import com.rafcode.schedulefootball.api.response.Players
import com.rafcode.schedulefootball.api.response.Teams
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback

interface PlayerView : ApiRepositoryCallback<Players> {
    fun onShowLoadingPlayer()
    fun onHideLoadingPlayer()
}