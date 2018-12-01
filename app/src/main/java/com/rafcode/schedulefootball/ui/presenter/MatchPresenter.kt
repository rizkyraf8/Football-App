package com.rafcode.schedulefootball.ui.presenter

import android.util.Log
import com.google.gson.Gson
import com.rafcode.schedulefootball.api.response.Events
import com.rafcode.schedulefootball.api.response.EventsSearch
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback
import com.rafcode.schedulefootball.ui.view.MatchView

class MatchPresenter(private val view: MatchView, private val apiRepository: ApiRepository) {

    fun getNextMatch(id: String) {
        view.onShowLoadingMatch()
        apiRepository.getNextMatch(id, object : ApiRepositoryCallback<Events?> {
            override fun onDataLoaded(data: Events?) {
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onDataError()
            }
        })
        view.onHideLoadingMatch()
    }

    fun getLastMatch(id: String) {
        view.onShowLoadingMatch()
        apiRepository.getLastMatch(id, object : ApiRepositoryCallback<Events?> {
            override fun onDataLoaded(data: Events?) {
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onDataError()
            }
        })
        view.onHideLoadingMatch()
    }
}