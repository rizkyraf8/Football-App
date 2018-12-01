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
import com.rafcode.schedulefootball.api.response.Event
import com.rafcode.schedulefootball.api.response.Events
import com.rafcode.schedulefootball.api.response.League
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.ui.adapter.MatchAdapter
import com.rafcode.schedulefootball.ui.presenter.MatchPresenter
import com.rafcode.schedulefootball.ui.view.MatchView
import com.rafcode.schedulefootball.utils.database
import com.rafcode.schedulefootball.utils.database.LeagueDatabase
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

open class NextMatchFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var fragment: NextMatchFragment? = null

    lateinit var mAdapter: MatchAdapter
    var mEvent = ArrayList<Event>()

    private lateinit var listLeagues: ArrayList<League>
    private lateinit var matchPresenter: MatchPresenter

    fun getInstance(): NextMatchFragment {
        if (fragment == null) {
            fragment = NextMatchFragment()
        }
        return fragment as NextMatchFragment
    }

    fun clearInstance() {
        if (fragment != null) fragment = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        srlMatch.setOnRefreshListener(this)

        initRv()
        initLeague()
    }

    private fun initRv() {
        val glmanager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mAdapter = MatchAdapter(activity!!, mEvent, "next")
        rvMatch.layoutManager = glmanager
        rvMatch.adapter = mAdapter
        rvMatch.hasFixedSize()
        rvMatch.isNestedScrollingEnabled = true
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
                initMatch(listLeagues[position].idLeague.toString())
            }

        }
    }

    fun initMatch(id: String) {
        mAdapter.clear()
        pbLoading.visibility = View.VISIBLE
        matchPresenter = MatchPresenter(object : MatchView {
            override fun onShowLoadingMatch() {
                srlMatch.isRefreshing = true
            }

            override fun onHideLoadingMatch() {
                srlMatch.isRefreshing = false
            }

            override fun onDataLoaded(data: Events?) {
                data?.events?.forEach { data ->
                    mAdapter.reCreate(data)
                }
                pbLoading.visibility = View.GONE
            }

            override fun onDataError() {
            }
        }, ApiRepository())

        matchPresenter.getNextMatch(id)
    }

    override fun onRefresh() {
        initMatch(listLeagues[spinner.selectedItemPosition].idLeague.toString())
    }

}
