package com.rafcode.schedulefootball.ui.presenter

import com.rafcode.schedulefootball.api.request.AuthRequest
import com.rafcode.schedulefootball.api.response.AuthResponse
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback
import com.rafcode.schedulefootball.repository.AuthView

class AuthPresenter(private val view: AuthView, private val apiRepository: ApiRepository) {

    fun getAuthLogin(params: AuthRequest) {
        view.onShowLoadingAuth()
        apiRepository.getAuthLogin(params, object : ApiRepositoryCallback<AuthResponse?> {
            override fun onDataLoaded(data: AuthResponse?) {
                view.onHideLoadingAuth()
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onHideLoadingAuth()
                view.onDataError()
            }
        })
    }

    fun getAuthRegister(params: AuthRequest) {
        view.onShowLoadingAuth()
        apiRepository.getAuthRegister(params, object : ApiRepositoryCallback<AuthResponse?> {
            override fun onDataLoaded(data: AuthResponse?) {
                view.onHideLoadingAuth()
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onHideLoadingAuth()
                view.onDataError()
            }
        })
    }
}