package com.rafcode.schedulefootball.ui.fragment

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.League
import com.rafcode.schedulefootball.api.response.Team
import com.rafcode.schedulefootball.api.response.TeamResponse
import com.rafcode.schedulefootball.databinding.FragmentTeamBinding
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.TeamView
import com.rafcode.schedulefootball.ui.adapter.TeamAdapter
import com.rafcode.schedulefootball.ui.presenter.TeamPresenter
import com.rafcode.schedulefootball.utils.database
import com.rafcode.schedulefootball.utils.database.LeagueDatabase
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

open class TeamFragment : BaseFragment<FragmentTeamBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    object Companion {
        fun newInstance(): TeamFragment {
            return TeamFragment()
        }
    }

    private lateinit var mAdapter: TeamAdapter
    private var mTeam = ArrayList<Team>()

    private lateinit var listLeagues: ArrayList<League>
    private lateinit var teamPresenter: TeamPresenter

    override fun onFragmentCreated() {
        binding.srlMatch.setOnRefreshListener(this)

        initRv()
        initLeague()
    }

    override fun onFragmentClick() {

    }

    override fun layout(): Int {
        return R.layout.fragment_team
    }

    private fun initRv() {
        val glmanager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        mAdapter = TeamAdapter(activity!!, mTeam)
        binding.rvTeam.layoutManager = glmanager
        binding.rvTeam.adapter = mAdapter
        binding.rvTeam.hasFixedSize()
        binding.rvTeam.isNestedScrollingEnabled = true

    }

    private fun initLeague() {
        listLeagues = ArrayList()
        listLeagues.clear()
        activity!!.database.use {
            mAdapter.clear()
            val result = select(LeagueDatabase.TABLE_LEAGUE)
            val league = result.parseList(classParser<LeagueDatabase>())
            league.forEach { data ->
                val item = League()
                item.idLeague = data.idLeague.toString()
                item.strLeague = data.strLeague
                item.strSport = data.strSport
                item.strLeagueAlternate = data.strLeagueAlternate

                listLeagues.add(item)
            }

        }

        populateLeague()
    }

    private fun populateLeague() {
        val spinnerLeague = ArrayList<String>()
        listLeagues.forEach { data ->
            spinnerLeague.add(data.strLeague.toString())
        }

        val adapter = ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            spinnerLeague
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                initTeam(listLeagues[position].idLeague.toString())
            }

        }
    }

    fun initTeam(id: String) {
        mAdapter.clear()
        binding.pbLoading.visibility = View.VISIBLE
        teamPresenter = TeamPresenter(object : TeamView {
            override fun onShowLoadingTeam() {
//                binding.srlMatch.isRefreshing = true
            }

            override fun onHideLoadingTeam() {
                binding.srlMatch.isRefreshing = false
            }

            override fun onDataLoaded(data: TeamResponse?) {
                data?.teams?.let { item ->
                    mAdapter.reCreate(item)
                }
                binding.pbLoading.visibility = View.GONE
            }

            override fun onDataError() {
            }
        }, ApiRepository())

        teamPresenter.getTeamLeague(getUser().token.toString(), id)
    }

    override fun onRefresh() {
        initTeam(listLeagues[binding.spinner.selectedItemPosition].idLeague.toString())
    }

}
