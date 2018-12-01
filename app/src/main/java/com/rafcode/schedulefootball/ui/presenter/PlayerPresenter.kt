package com.rafcode.schedulefootball.ui.presenter

import com.rafcode.schedulefootball.api.response.Players
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback
import com.rafcode.schedulefootball.ui.view.PlayerView

class PlayerPresenter(private val view: PlayerView, private val apiRepository: ApiRepository) {

    fun getPlayerLeague(id: String) {
        view.onShowLoadingPlayer()
        apiRepository.getPlayerTeam(id, object : ApiRepositoryCallback<Players?> {
            override fun onDataLoaded(data: Players?) {
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onDataError()
            }
        })
        view.onHideLoadingPlayer()
    }
}