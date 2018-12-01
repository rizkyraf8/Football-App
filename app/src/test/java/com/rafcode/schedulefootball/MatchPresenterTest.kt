package com.rafcode.schedulefootball

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.rafcode.schedulefootball.api.response.Events
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.ApiRepositoryCallback
import com.rafcode.schedulefootball.ui.presenter.MatchPresenter
import com.rafcode.schedulefootball.ui.view.MatchView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MatchPresenterTest {
    @Mock
    private lateinit var view: MatchView
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var events: Events
    private lateinit var matchPresenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        matchPresenter = MatchPresenter(view, apiRepository)
    }

    @Test
    fun getMatchDataTest() {

        matchPresenter.getNextMatch("4328")

        argumentCaptor<ApiRepositoryCallback<Events?>>().apply {
            verify(apiRepository).getNextMatch(eq("4328"), capture())
            firstValue.onDataLoaded(events)
        }
        Mockito.verify(view).onDataLoaded(events)
    }

    @Test
    fun getMatchNullTest() {
        matchPresenter.getNextMatch("")
        argumentCaptor<ApiRepositoryCallback<Events?>>().apply {
            verify(apiRepository).getNextMatch(eq(""), capture())
            firstValue.onDataError()
        }
        Mockito.verify(view).onDataError()
    }
}
