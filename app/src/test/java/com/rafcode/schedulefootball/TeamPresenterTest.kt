package com.rafcode.schedulefootball

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.rafcode.schedulefootball.api.response.TeamResponse
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback
import com.rafcode.schedulefootball.repository.TeamView
import com.rafcode.schedulefootball.ui.presenter.TeamPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TeamPresenterTest {
    @Mock
    private lateinit var view: TeamView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var teams: TeamResponse
    private lateinit var teamPresenter: TeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        teamPresenter = TeamPresenter(view, apiRepository)
    }

    @Test
    fun getTeamDataTest() {

        teamPresenter.getTeamLeague("", "4328")

        argumentCaptor<ApiRepositoryCallback<TeamResponse?>>().apply {
            verify(apiRepository).getTeamLeague(eq(""), eq("4328"), capture())
            firstValue.onDataLoaded(teams)
        }
        verify(view).onDataLoaded(teams)
    }

    @Test
    fun getTeamNullTest() {
        teamPresenter.getTeamLeague("", "")
        argumentCaptor<ApiRepositoryCallback<TeamResponse?>>().apply {
            verify(apiRepository).getTeamLeague(eq(""), eq(""), capture())
            firstValue.onDataError()
        }
        verify(view).onDataError()
    }
}
