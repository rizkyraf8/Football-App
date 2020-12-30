package com.rafcode.schedulefootball.ui.presenter

import com.rafcode.schedulefootball.api.response.EventsSearch
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback
import com.rafcode.schedulefootball.repository.MatchSeacrhView

class MatchSearchPresenter(
    private val view: MatchSeacrhView,
    private val apiRepository: ApiRepository
) {

    fun searchMatch(token: String, e: String) {
        view.onShowLoadingMatch()
        apiRepository.getSearchEvent(token, e, object : ApiRepositoryCallback<EventsSearch?> {
            override fun onDataLoaded(data: EventsSearch?) {
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