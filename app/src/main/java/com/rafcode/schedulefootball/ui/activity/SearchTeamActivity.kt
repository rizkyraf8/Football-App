package com.rafcode.schedulefootball.ui.activity

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Team
import com.rafcode.schedulefootball.api.response.Teams
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.ui.adapter.TeamAdapter
import com.rafcode.schedulefootball.ui.presenter.TeamPresenter
import com.rafcode.schedulefootball.ui.view.TeamView
import kotlinx.android.synthetic.main.activity_search_team.*
import kotlinx.android.synthetic.main.content_search_team.*

class SearchTeamActivity : AppCompatActivity() {

    lateinit var mAdapter: TeamAdapter
    var mTeam = ArrayList<Team>()
    var key = ""

    private lateinit var teamPresenter: TeamPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_team)

        key = intent.getStringExtra("key")

        initToolbar()


        initRv()
        initTeam(key)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = key
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRv() {
        val glmanager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter = TeamAdapter(this, mTeam)
        rvTeam.layoutManager = glmanager
        rvTeam.adapter = mAdapter
        rvTeam.hasFixedSize()
        rvTeam.isNestedScrollingEnabled = true

    }

    fun initTeam(team: String) {
        mAdapter.clear()
        pbLoading.visibility = View.VISIBLE
        teamPresenter = TeamPresenter(object : TeamView {
            override fun onShowLoadingTeam() {
            }

            override fun onHideLoadingTeam() {
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

        teamPresenter.getTeamSearch(team)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val sManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val sView = menu.findItem(R.id.menu_se).actionView as SearchView
        sView.setSearchableInfo(sManager.getSearchableInfo(componentName))
        sView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                initTeam(query)
                supportActionBar?.title = query
                return true
            }
        })

        return true
    }
}
