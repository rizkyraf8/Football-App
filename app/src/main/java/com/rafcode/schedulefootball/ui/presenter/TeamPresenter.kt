package com.rafcode.schedulefootball.ui.presenter

import com.rafcode.schedulefootball.api.response.Teams
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback
import com.rafcode.schedulefootball.ui.view.TeamView

class TeamPresenter(private val view: TeamView, private val apiRepository: ApiRepository) {

    fun getTeamLeague(id: String) {
        view.onShowLoadingTeam()
        apiRepository.getTeamLeague(id, object : ApiRepositoryCallback<Teams?> {
            override fun onDataLoaded(data: Teams?) {
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onDataError()
            }
        })
        view.onHideLoadingTeam()
    }
    fun getTeamSearch(team: String) {
        view.onShowLoadingTeam()
        apiRepository.getSearchTeam(team, object : ApiRepositoryCallback<Teams?> {
            override fun onDataLoaded(data: Teams?) {
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onDataError()
            }
        })
        view.onHideLoadingTeam()
    }
}