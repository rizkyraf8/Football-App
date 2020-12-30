package com.rafcode.schedulefootball.ui.fragment

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Event
import com.rafcode.schedulefootball.api.response.EventResponse
import com.rafcode.schedulefootball.api.response.League
import com.rafcode.schedulefootball.databinding.FragmentMatchBinding
import com.rafcode.schedulefootball.repository.ApiRepository
import com.rafcode.schedulefootball.repository.MatchView
import com.rafcode.schedulefootball.ui.adapter.MatchAdapter
import com.rafcode.schedulefootball.ui.presenter.MatchPresenter
import com.rafcode.schedulefootball.utils.database
import com.rafcode.schedulefootball.utils.database.LeagueDatabase
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select


class LastMatchFragment : BaseFragment<FragmentMatchBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    private var fragment: LastMatchFragment? = null

    private lateinit var mAdapter: MatchAdapter
    private var mEvent = ArrayList<Event>()

    private lateinit var listLeagues: ArrayList<League>
    private lateinit var matchPresenter: MatchPresenter

    fun getInstance(): LastMatchFragment {
        if (fragment == null) {
            fragment = LastMatchFragment()
        }

        return fragment as LastMatchFragment
    }

    override fun onFragmentCreated() {
        binding.srlMatch.setOnRefreshListener(this)

        initRv()
        initLeague()
    }

    override fun onFragmentClick() {

    }

    override fun layout(): Int {
        return R.layout.fragment_match
    }

    private fun initRv() {
        val glmanager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        mAdapter = MatchAdapter(mEvent, "last")
        binding.rvMatch.layoutManager = glmanager
        binding.rvMatch.adapter = mAdapter
        binding.rvMatch.hasFixedSize()
        binding.rvMatch.isNestedScrollingEnabled = true
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
                initMatch(listLeagues[position].idLeague.toString())
            }

        }
    }

    fun initMatch(id: String) {
        mAdapter.clear()
        binding.pbLoading.visibility = View.VISIBLE
        matchPresenter = MatchPresenter(object : MatchView {
            override fun onShowLoadingMatch() {
//                binding.srlMatch.isRefreshing = true
            }

            override fun onHideLoadingMatch() {
                binding.srlMatch.isRefreshing = false
            }

            override fun onDataLoaded(data: EventResponse?) {
                data?.events?.let { item ->
                    mAdapter.reCreate(item)
                }
                binding.pbLoading.visibility = View.GONE
            }

            override fun onDataError() {
            }
        }, ApiRepository())

        matchPresenter.getLastMatch(getUser().token.toString(), id)
    }

    override fun onRefresh() {
        initMatch(listLeagues[binding.spinner.selectedItemPosition].idLeague.toString())
    }
}
