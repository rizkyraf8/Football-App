package com.rafcode.schedulefootball.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Player
import com.rafcode.schedulefootball.api.response.PlayerResponse
import com.rafcode.schedulefootball.databinding.FragmentListTeamBinding
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.PlayerView
import com.rafcode.schedulefootball.ui.adapter.PlayerAdapter
import com.rafcode.schedulefootball.ui.presenter.PlayerPresenter

open class TeamListPlayerFragment(private val idTeam: String) :
    BaseFragment<FragmentListTeamBinding>() {

    private var fragment: TeamListPlayerFragment? = null

    private lateinit var mAdapter: PlayerAdapter
    private var mPlayer = ArrayList<Player>()

    private lateinit var matchPresenter: PlayerPresenter

    fun getInstance(): TeamListPlayerFragment {
        if (fragment == null) {
            fragment = TeamListPlayerFragment(idTeam)
        }
        return fragment as TeamListPlayerFragment
    }

    override fun onFragmentCreated() {
        initRv()
        initPlayer()
    }

    override fun onFragmentClick() {
    }

    override fun layout(): Int {
        return R.layout.fragment_list_team
    }

    private fun initRv() {
        val glmanager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        mAdapter = PlayerAdapter(mPlayer)
        binding.rvPlayer.layoutManager = glmanager
        binding.rvPlayer.adapter = mAdapter
        binding.rvPlayer.hasFixedSize()
        binding.rvPlayer.isNestedScrollingEnabled = true
    }


    fun initPlayer() {
        matchPresenter = PlayerPresenter(object : PlayerView {
            override fun onShowLoadingPlayer() {
            }

            override fun onHideLoadingPlayer() {
            }

            override fun onDataLoaded(data: PlayerResponse?) {
                mAdapter.clear()
                data?.player?.let { item ->
                    mAdapter.reCreate(item)
                }
            }

            override fun onDataError() {
            }
        }, ApiRepository())

        matchPresenter.getPlayerLeague(getUser().token.toString(), idTeam)
    }

}
