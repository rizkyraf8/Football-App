package com.rafcode.schedulefootball

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.rafcode.schedulefootball.api.response.Events
import com.rafcode.schedulefootball.api.response.Teams
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback
import com.rafcode.schedulefootball.ui.presenter.TeamPresenter
import com.rafcode.schedulefootball.ui.view.TeamView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TeamPresenterTest {
    @Mock
    private lateinit var view: TeamView
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var teams: Teams
    private lateinit var teamPresenter: TeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        teamPresenter = TeamPresenter(view, apiRepository)
    }

    @Test
    fun getTeamDataTest() {

        teamPresenter.getTeamLeague("4328")

        argumentCaptor<ApiRepositoryCallback<Teams?>>().apply {
            verify(apiRepository).getTeamLeague(eq("4328"), capture())
            firstValue.onDataLoaded(teams)
        }
        Mockito.verify(view).onDataLoaded(teams)
    }

    @Test
    fun getTeamNullTest() {
        teamPresenter.getTeamLeague("")
        argumentCaptor<ApiRepositoryCallback<Teams?>>().apply {
            verify(apiRepository).getTeamLeague(eq(""), capture())
            firstValue.onDataError()
        }
        Mockito.verify(view).onDataError()
    }
}
