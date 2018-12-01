package com.rafcode.schedulefootball.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.League
import com.rafcode.schedulefootball.api.response.Leagues
import com.rafcode.schedulefootball.api.response.Team
import com.rafcode.schedulefootball.api.response.Teams
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.ui.adapter.TeamAdapter
import com.rafcode.schedulefootball.ui.presenter.LeaguePresenter
import com.rafcode.schedulefootball.ui.presenter.TeamPresenter
import com.rafcode.schedulefootball.ui.view.LeagueView
import com.rafcode.schedulefootball.ui.view.TeamView
import com.rafcode.schedulefootball.utils.database
import com.rafcode.schedulefootball.utils.database.LeagueDatabase
import kotlinx.android.synthetic.main.fragment_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

open class TeamFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var fragment: TeamFragment? = null

    lateinit var mAdapter: TeamAdapter
    var mTeam = ArrayList<Team>()

    private lateinit var listLeagues: ArrayList<League>
    private lateinit var teamPresenter: TeamPresenter

    fun newInstance(): TeamFragment {
        if (fragment == null) {
            fragment = TeamFragment()
        }
        return fragment as TeamFragment
    }

    fun clearInstance() {
        if (fragment != null) fragment = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        srlMatch.setOnRefreshListener(this)

        initRv()
        initLeague()
    }

    private fun initRv() {
        val glmanager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mAdapter = TeamAdapter(activity!!, mTeam)
        rvTeam.layoutManager = glmanager
        rvTeam.adapter = mAdapter
        rvTeam.hasFixedSize()
        rvTeam.isNestedScrollingEnabled = true

    }

    private fun initLeague() {
        listLeagues = ArrayList()
        listLeagues.clear()
        activity!!.database.use {
            mAdapter.clear()
            val result = select(LeagueDatabase.TABLE_LEAGUE)
            val league = result.parseList(classParser<LeagueDatabase>())
            league.forEach { data ->
                var league = League()
                league.idLeague = data.idLeague.toString()
                league.strLeague = data.strLeague
                league.strSport = data.strSport
                league.strLeagueAlternate = data.strLeagueAlternate

                listLeagues.add(league)
            }

        }

        populateLeague()
    }

    fun populateLeague() {
        val spinnerLeague = ArrayList<String>()
        listLeagues.forEach { data ->
            spinnerLeague.add(data.strLeague.toString())
        }

        val adapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, spinnerLeague)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                initTeam(listLeagues[position].idLeague.toString())
            }

        }
    }

    fun initTeam(id: String) {
        mAdapter.clear()
        pbLoading.visibility = View.VISIBLE
        teamPresenter = TeamPresenter(object : TeamView {
            override fun onShowLoadingTeam() {
                srlMatch.isRefreshing = true
            }

            override fun onHideLoadingTeam() {
                srlMatch.isRefreshing = false
            }

            override fun onDataLoaded(data: Teams?) {
                data?.teams?.forEach { data ->
                    mAdapter.reCreate(data)
                }
                pbLoading.visibility = View.GONE
            }

            override fun onDataError() {
            }
        }, ApiRepository())

        teamPresenter.getTeamLeague(id)
    }

    override fun onRefresh() {
        initTeam(listLeagues[spinner.selectedItemPosition].idLeague.toString())
    }

}
