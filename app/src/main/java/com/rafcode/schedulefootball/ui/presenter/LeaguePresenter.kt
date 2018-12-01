package com.rafcode.schedulefootball.ui.presenter

import android.util.Log
import com.google.gson.Gson
import com.rafcode.schedulefootball.api.response.Leagues
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback
import com.rafcode.schedulefootball.ui.view.LeagueView

class LeaguePresenter(private val view: LeagueView, private val apiRepository: ApiRepository) {

    fun getAllLeague() {
        apiRepository.getAllLeagues(object : ApiRepositoryCallback<Leagues?> {
            override fun onDataLoaded(data: Leagues?) {
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onDataError()
            }
        })
    }
}