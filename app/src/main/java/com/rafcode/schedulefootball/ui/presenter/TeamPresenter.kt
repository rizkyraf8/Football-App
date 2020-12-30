package com.rafcode.schedulefootball.ui.presenter

import com.rafcode.schedulefootball.api.response.TeamResponse
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback
import com.rafcode.schedulefootball.repository.TeamView

class TeamPresenter(private val view: TeamView, private val apiRepository: ApiRepository) {

    fun getTeamLeague(token: String, id: String) {
        view.onShowLoadingTeam()
        apiRepository.getTeamLeague(token, id, object : ApiRepositoryCallback<TeamResponse?> {
            override fun onDataLoaded(data: TeamResponse?) {
                view.onHideLoadingTeam()
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onHideLoadingTeam()
                view.onDataError()
            }
        })
    }

    fun getTeamSearch(token: String, team: String) {
        view.onShowLoadingTeam()
        apiRepository.getSearchTeam(token, team, object : ApiRepositoryCallback<TeamResponse?> {
            override fun onDataLoaded(data: TeamResponse?) {
                view.onHideLoadingTeam()
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onHideLoadingTeam()
                view.onDataError()
            }
        })
    }
}