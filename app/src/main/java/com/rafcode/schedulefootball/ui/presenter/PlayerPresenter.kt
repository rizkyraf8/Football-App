package com.rafcode.schedulefootball.ui.presenter

import com.rafcode.schedulefootball.api.response.PlayerResponse
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback
import com.rafcode.schedulefootball.repository.PlayerView

class PlayerPresenter(private val view: PlayerView, private val apiRepository: ApiRepository) {

    fun getPlayerLeague(token: String, id: String) {
        view.onShowLoadingPlayer()
        apiRepository.getPlayerTeam(token, id, object : ApiRepositoryCallback<PlayerResponse?> {
            override fun onDataLoaded(data: PlayerResponse?) {
                view.onHideLoadingPlayer()
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onHideLoadingPlayer()
                view.onDataError()
            }
        })
    }
}