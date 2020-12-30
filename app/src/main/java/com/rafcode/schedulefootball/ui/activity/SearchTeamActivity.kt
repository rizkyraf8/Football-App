package com.rafcode.schedulefootball.ui.activity

import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Team
import com.rafcode.schedulefootball.api.response.TeamResponse
import com.rafcode.schedulefootball.databinding.ActivitySearchTeamBinding
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.TeamView
import com.rafcode.schedulefootball.ui.adapter.TeamAdapter
import com.rafcode.schedulefootball.ui.presenter.TeamPresenter

class SearchTeamActivity : BaseActivity<ActivitySearchTeamBinding>() {

    override fun getViewBinding(): ActivitySearchTeamBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_search_team)
    }

    override fun onActivityCreated() {
        initToolbar()
        initRv()
        initTeam(key)
    }

    override fun onActivityClick() {

    }

    lateinit var mAdapter: TeamAdapter
    private var mTeam = ArrayList<Team>()
    private val key: String by lazy {
        intent.getStringExtra("key").toString()
    }

    private lateinit var teamPresenter: TeamPresenter

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = key
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRv() {
        val glmanager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        mAdapter = TeamAdapter(this, mTeam)
        binding.layout.rvTeam.layoutManager = glmanager
        binding.layout.rvTeam.adapter = mAdapter
        binding.layout.rvTeam.hasFixedSize()
        binding.layout.rvTeam.isNestedScrollingEnabled = true

    }

    fun initTeam(team: String) {
        mAdapter.clear()
        binding.layout.pbLoading.visibility = View.VISIBLE
        teamPresenter = TeamPresenter(object : TeamView {
            override fun onShowLoadingTeam() {
            }

            override fun onHideLoadingTeam() {
            }

            override fun onDataLoaded(data: TeamResponse?) {
                data?.teams?.let { item ->
                    mAdapter.reCreate(item)
                }
                binding.layout.pbLoading.visibility = View.GONE
            }

            override fun onDataError() {
            }
        }, ApiRepository())

        teamPresenter.getTeamSearch(getUser().token.toString(), team)
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
