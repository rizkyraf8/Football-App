package com.rafcode.schedulefootball.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Team
import com.rafcode.schedulefootball.databinding.FragmentTeamFavoriteBinding
import com.rafcode.schedulefootball.ui.adapter.TeamAdapter
import com.rafcode.schedulefootball.utils.database
import com.rafcode.schedulefootball.utils.database.FavoriteTeam
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

open class FavoriteTeamFragment : BaseFragment<FragmentTeamFavoriteBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    private var fragment: FavoriteTeamFragment? = null
    lateinit var mAdapter: TeamAdapter
    var mTeam = ArrayList<Team>()

    override fun onFragmentCreated() {
        initAdapter()
        binding.srlMatch.setOnRefreshListener(this)
    }

    override fun onFragmentClick() {

    }

    override fun layout(): Int {
        return R.layout.fragment_team_favorite
    }

    fun getInstance(): FavoriteTeamFragment {
        if (fragment == null) {
            fragment = FavoriteTeamFragment()
        }

        return fragment as FavoriteTeamFragment
    }

    private fun initAdapter() {
        val glmanager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        mAdapter = TeamAdapter(activity!!, mTeam)
        binding.rvMatch.layoutManager = glmanager
        binding.rvMatch.adapter = mAdapter
        binding.rvMatch.hasFixedSize()
        binding.rvMatch.isNestedScrollingEnabled = true
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    override fun onRefresh() {
        initData()
    }

    private fun initData() {
        if (binding.srlMatch.isRefreshing) binding.srlMatch.isRefreshing = false
        activity!!.database.use {
            mAdapter.clear()
            val result = select(FavoriteTeam.TABLE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            val list = ArrayList<Team>()
            favorite.forEach { data ->
                val match = Gson().fromJson(data.gson, Team::class.java)
                list.add(match)
            }
            mAdapter.reCreate(list)

            if (mAdapter.getData().isEmpty()) {
                binding.tvNoData.visibility = View.VISIBLE
            } else {
                binding.tvNoData.visibility = View.GONE
            }
        }
    }
}
