package com.rafcode.schedulefootball.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Player
import com.rafcode.schedulefootball.api.response.Players
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.ui.adapter.PlayerAdapter
import com.rafcode.schedulefootball.ui.presenter.PlayerPresenter
import com.rafcode.schedulefootball.ui.view.PlayerView
import com.rafcode.schedulefootball.utils.TempData
import kotlinx.android.synthetic.main.fragment_list_team.*

open class TeamListPlayerFragment : Fragment(){

    private var fragment: TeamListPlayerFragment? = null

    lateinit var mAdapter: PlayerAdapter
    var mPlayer = ArrayList<Player>()

    private lateinit var matchPresenter: PlayerPresenter

    fun getInstance(): TeamListPlayerFragment {
        if (fragment == null) {
            fragment = TeamListPlayerFragment()
        }
        return fragment as TeamListPlayerFragment
    }

    fun clearInstance() {
        if (fragment != null) fragment = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        initPlayer()
    }

    private fun initRv() {
        val glmanager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mAdapter = PlayerAdapter(activity!!, mPlayer)
        rvPlayer.layoutManager = glmanager
        rvPlayer.adapter = mAdapter
        rvPlayer.hasFixedSize()
        rvPlayer.isNestedScrollingEnabled = true
    }


    fun initPlayer() {
        matchPresenter = PlayerPresenter(object : PlayerView {
            override fun onShowLoadingPlayer() {
            }

            override fun onHideLoadingPlayer() {
            }

            override fun onDataLoaded(data: Players?) {
                mAdapter.clear()
                data?.player?.forEach { data ->
                    mAdapter.reCreate(data)
                }
            }

            override fun onDataError() {
            }
        }, ApiRepository())

        matchPresenter.getPlayerLeague(TempData.teams?.idTeam.toString())
    }

}
