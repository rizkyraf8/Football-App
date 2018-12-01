package com.rafcode.schedulefootball.ui.presenter

import android.util.Log
import com.google.gson.Gson
import com.rafcode.schedulefootball.api.response.EventsSearch
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback
import com.rafcode.schedulefootball.ui.view.MatchSeacrhView

class MatchSearchPresenter(private val view: MatchSeacrhView, private val apiRepository: ApiRepository) {

    fun searchMatch(e: String) {
        view.onShowLoadingMatch()
        apiRepository.getSearchEvent(e, object : ApiRepositoryCallback<EventsSearch?> {
            override fun onDataLoaded(data: EventsSearch?) {
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onDataError()
            }
        })
        view.onHideLoadingMatch()
    }
}