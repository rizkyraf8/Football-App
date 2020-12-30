package com.rafcode.schedulefootball.ui.activity

import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Event
import com.rafcode.schedulefootball.api.response.EventsSearch
import com.rafcode.schedulefootball.databinding.ActivitySearchMatchBinding
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.MatchSeacrhView
import com.rafcode.schedulefootball.ui.adapter.MatchAdapter
import com.rafcode.schedulefootball.ui.presenter.MatchSearchPresenter

class SearchMatchActivity : BaseActivity<ActivitySearchMatchBinding>() {

    override fun getViewBinding(): ActivitySearchMatchBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_search_match)
    }

    override fun onActivityCreated() {
        initToolbar()
        initRv()
        initMatch(key)
    }

    override fun onActivityClick() {

    }

    lateinit var mAdapter: MatchAdapter
    private var mEvent = ArrayList<Event>()
    private val key: String by lazy {
        intent.getStringExtra("key").toString()
    }

    private lateinit var matchPresenter: MatchSearchPresenter

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
        mAdapter = MatchAdapter(mEvent, "search")
        binding.layout.rvMatch.layoutManager = glmanager
        binding.layout.rvMatch.adapter = mAdapter
        binding.layout.rvMatch.hasFixedSize()
        binding.layout.rvMatch.isNestedScrollingEnabled = true
    }

    private fun initMatch(event: String) {
        mAdapter.clear()
        binding.layout.pbLoading.visibility = View.VISIBLE
        matchPresenter = MatchSearchPresenter(object : MatchSeacrhView {
            override fun onShowLoadingMatch() {
            }

            override fun onHideLoadingMatch() {
            }

            override fun onDataLoaded(data: EventsSearch?) {
                data?.events?.let { item ->
                    mAdapter.reCreate(item)
                }
                binding.layout.pbLoading.visibility = View.GONE
            }

            override fun onDataError() {
            }
        }, ApiRepository())

        matchPresenter.searchMatch(getUser().token.toString(), event)
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
