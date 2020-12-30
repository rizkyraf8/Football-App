package com.rafcode.schedulefootball.ui.presenter

import com.rafcode.schedulefootball.api.response.LeagueResponse
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback
import com.rafcode.schedulefootball.repository.LeagueView

class LeaguePresenter(private val view: LeagueView, private val apiRepository: ApiRepository) {

    fun getAllLeague(token: String) {
        view.onShowLoadingLeague()
        apiRepository.getAllLeagues(token, object : ApiRepositoryCallback<LeagueResponse?> {
            override fun onDataLoaded(data: LeagueResponse?) {
                view.onHideLoadingLeague()
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onHideLoadingLeague()
                view.onDataError()
            }
        })
    }
}