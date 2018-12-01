package com.rafcode.schedulefootball.repository

interface ApiRepositoryCallback<T> {

    fun onDataLoaded(data: T?)
    fun onDataError()
}