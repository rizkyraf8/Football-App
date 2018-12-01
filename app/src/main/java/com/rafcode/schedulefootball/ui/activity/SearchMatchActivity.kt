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
import com.rafcode.schedulefootball.api.response.Event
import com.rafcode.schedulefootball.api.response.EventsSearch
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.ui.adapter.MatchAdapter
import com.rafcode.schedulefootball.ui.presenter.MatchSearchPresenter
import com.rafcode.schedulefootball.ui.view.MatchSeacrhView
import kotlinx.android.synthetic.main.activity_search_match.*
import kotlinx.android.synthetic.main.content_search_match.*

class SearchMatchActivity : AppCompatActivity() {

    lateinit var mAdapter: MatchAdapter
    var mEvent = ArrayList<Event>()
    var key = ""

    private lateinit var matchPresenter: MatchSearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)

        key = intent.getStringExtra("key")

        initToolbar()

        initRv()
        initMatch(key)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = key
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRv() {
        val glmanager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter = MatchAdapter(this, mEvent, "search")
        rvMatch.layoutManager = glmanager
        rvMatch.adapter = mAdapter
        rvMatch.hasFixedSize()
        rvMatch.isNestedScrollingEnabled = true
    }

    private fun initMatch(event: String) {
        mAdapter.clear()
        pbLoading.visibility = View.VISIBLE
        matchPresenter = MatchSearchPresenter(object : MatchSeacrhView {
            override fun onShowLoadingMatch() {
            }

            override fun onHideLoadingMatch() {
            }

            override fun onDataLoaded(data: EventsSearch?) {
                data?.events?.forEach { data ->
                    mAdapter.reCreate(data)
                }
                pbLoading.visibility = View.GONE
            }

            override fun onDataError() {
            }
        }, ApiRepository())

        matchPresenter.searchMatch(event)
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
                initMatch(query)
                supportActionBar?.title = query
                return true
            }
        })

        return true
    }

}
