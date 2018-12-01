package com.rafcode.schedulefootball.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Team
import com.rafcode.schedulefootball.ui.adapter.TeamAdapter
import com.rafcode.schedulefootball.utils.database
import com.rafcode.schedulefootball.utils.database.FavoriteMatch
import com.rafcode.schedulefootball.utils.database.FavoriteTeam
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

open class FavoriteTeamFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var fragment: FavoriteTeamFragment? = null
    lateinit var mAdapter: TeamAdapter
    var mTeam = ArrayList<Team>()


    fun getInstance(): FavoriteTeamFragment {
        if (fragment == null) {
            fragment = FavoriteTeamFragment()
        }

        return fragment as FavoriteTeamFragment
    }

    fun clearInstance() {
        if (fragment != null) fragment = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        srlMatch.setOnRefreshListener(this)

        val glmanager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mAdapter = TeamAdapter(activity!!, mTeam)
        rvMatch.layoutManager = glmanager
        rvMatch.adapter = mAdapter
        rvMatch.hasFixedSize()
        rvMatch.isNestedScrollingEnabled = true

    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    override fun onRefresh() {
        initData()
    }

    fun initData() {
        if (srlMatch.isRefreshing) srlMatch.isRefreshing = false
        activity!!.database.use {
            mAdapter.clear()
            val result = select(FavoriteTeam.TABLE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())

            favorite.forEach { data ->
                val match = Gson().fromJson(data.gson, Team::class.java)
                mAdapter.reCreate(match)
            }

        }
    }

}
