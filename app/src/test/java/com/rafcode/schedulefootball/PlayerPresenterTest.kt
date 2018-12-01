package com.rafcode.schedulefootball

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.rafcode.schedulefootball.api.response.Players
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback
import com.rafcode.schedulefootball.ui.presenter.PlayerPresenter
import com.rafcode.schedulefootball.ui.view.PlayerView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class PlayerPresenterTest {
    @Mock
    private lateinit var view: PlayerView
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var players: Players
    private lateinit var playerPresenter: PlayerPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        playerPresenter = PlayerPresenter(view, apiRepository)
    }

    @Test
    fun getPlayerDataTest() {

        playerPresenter.getPlayerLeague("133667")

        argumentCaptor<ApiRepositoryCallback<Players?>>().apply {
            verify(apiRepository).getPlayerTeam(eq("133667"), capture())
            firstValue.onDataLoaded(players)
        }
        Mockito.verify(view).onDataLoaded(players)
    }

    @Test
    fun getPlayerNullTest() {
        playerPresenter.getPlayerLeague("")
        argumentCaptor<ApiRepositoryCallback<Players?>>().apply {
            verify(apiRepository).getPlayerTeam(eq(""), capture())
            firstValue.onDataError()
        }
        Mockito.verify(view).onDataError()
    }
}
