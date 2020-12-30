package com.rafcode.schedulefootball.ui.presenter

import com.rafcode.schedulefootball.api.response.EventResponse
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback
import com.rafcode.schedulefootball.repository.MatchView

class MatchPresenter(private val view: MatchView, private val apiRepository: ApiRepository) {

    fun getNextMatch(token: String, id: String) {
        view.onShowLoadingMatch()
        apiRepository.getNextMatch(token, id, object : ApiRepositoryCallback<EventResponse?> {
            override fun onDataLoaded(data: EventResponse?) {
                view.onHideLoadingMatch()
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onHideLoadingMatch()
                view.onDataError()
            }
        })
    }

    fun getLastMatch(token: String, id: String) {
        view.onShowLoadingMatch()
        apiRepository.getLastMatch(token, id, object : ApiRepositoryCallback<EventResponse?> {
            override fun onDataLoaded(data: EventResponse?) {
                view.onHideLoadingMatch()
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onHideLoadingMatch()
                view.onDataError()
            }
        })
    }
}