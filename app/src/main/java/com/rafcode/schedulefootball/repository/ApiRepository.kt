package com.rafcode.schedulefootball.repository

import android.annotation.SuppressLint
import com.rafcode.schedulefootball.api.ApiService
import com.rafcode.schedulefootball.api.request.AuthRequest
import com.rafcode.schedulefootball.api.response.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApiRepository {

    @SuppressLint("CheckResult")
    fun getAuthLogin(params: AuthRequest, callback: ApiRepositoryCallback<AuthResponse?>) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.authLogin(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        data?.let {
                            callback.onDataLoaded(it)
                        } ?: run {
                            callback.onDataError()
                        }
                    },
                    {
                        callback.onDataError()
                    }
                )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
    fun getAuthRegister(params: AuthRequest, callback: ApiRepositoryCallback<AuthResponse?>) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.authRegister(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        data?.let {
                            callback.onDataLoaded(it)
                        } ?: run {
                            callback.onDataError()
                        }
                    },
                    {
                        callback.onDataError()
                    }
                )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
    fun getLastMatch(token: String, id: String, callback: ApiRepositoryCallback<EventResponse?>) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.lastEventLeague(token, id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        data?.let {
                            callback.onDataLoaded(it)
                        } ?: run {
                            callback.onDataError()
                        }
                    },
                    {
                        callback.onDataError()
                    }
                )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
    fun getNextMatch(token: String, id: String, callback: ApiRepositoryCallback<EventResponse?>) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.nextEventLeague(token, id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        data?.let {
                            callback.onDataLoaded(it)
                        } ?: run {
                            callback.onDataError()
                        }
                    },
                    {
                        callback.onDataError()
                    }
                )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
    fun getAllLeagues(token: String, callback: ApiRepositoryCallback<LeagueResponse?>) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.allLeagues(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        data?.let {
                            callback.onDataLoaded(it)
                        } ?: run {
                            callback.onDataError()
                        }
                    },
                    {
                        callback.onDataError()
                    }
                )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
    fun getTeamLeague(token: String, id: String, callback: ApiRepositoryCallback<TeamResponse?>) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.teamLeague(token, id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        data?.let {
                            callback.onDataLoaded(it)
                        } ?: run {
                            callback.onDataError()
                        }
                    },
                    {
                        callback.onDataError()
                    }
                )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
    fun getPlayerTeam(token: String, id: String, callback: ApiRepositoryCallback<PlayerResponse?>) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.listPlayerTeam(token, id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        data?.let {
                            callback.onDataLoaded(it)
                        } ?: run {
                            callback.onDataError()
                        }
                    },
                    {
                        callback.onDataError()
                    }
                )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
    fun getSearchTeam(token: String, team: String, callback: ApiRepositoryCallback<TeamResponse?>) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.searchTeam(token, team)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        data?.let {
                            callback.onDataLoaded(it)
                        } ?: run {
                            callback.onDataError()
                        }
                    },
                    {
                        callback.onDataError()
                    }
                )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
    fun getSearchEvent(
        token: String,
        event: String,
        callback: ApiRepositoryCallback<EventsSearch?>
    ) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.searchEvent(token, event)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        data?.let {
                            callback.onDataLoaded(it)
                        } ?: run {
                            callback.onDataError()
                        }
                    },
                    {
                        callback.onDataError()
                    }
                )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}