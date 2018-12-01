package com.rafcode.schedulefootball.repository

import android.util.Log
import com.google.gson.Gson
import com.rafcode.schedulefootball.api.ApiService
import com.rafcode.schedulefootball.api.response.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApiRepository {

    fun getLastMatch(id: String, callback: ApiRepositoryCallback<Events?>) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.lastEventLeague(id)
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

    fun getNextMatch(id: String, callback: ApiRepositoryCallback<Events?>) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.nextEventLeague(id)
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

    fun getAllLeagues(callback: ApiRepositoryCallback<Leagues?>) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.allLeagues()
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

    fun getTeamLeague(id: String, callback: ApiRepositoryCallback<Teams?>) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.teamLeague(id)
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

    fun getPlayerTeam(id: String, callback: ApiRepositoryCallback<Players?>) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.listPlayerTeam(id)
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

    fun getSearchTeam(team: String, callback: ApiRepositoryCallback<Teams?>) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.searchTeam(team)
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

    fun getSearchEvent(event: String, callback: ApiRepositoryCallback<EventsSearch?>) {
        try {
            val service: ApiService.Api = ApiService().getService()
            service.searchEvent(event)
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