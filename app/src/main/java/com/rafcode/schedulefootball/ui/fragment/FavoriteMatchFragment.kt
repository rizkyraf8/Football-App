package com.rafcode.schedulefootball.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Event
import com.rafcode.schedulefootball.databinding.FragmentMatchFavoriteBinding
import com.rafcode.schedulefootball.ui.adapter.MatchAdapter
import com.rafcode.schedulefootball.utils.database
import com.rafcode.schedulefootball.utils.database.FavoriteMatch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

open class FavoriteMatchFragment : BaseFragment<FragmentMatchFavoriteBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    private var fragment: FavoriteMatchFragment? = null
    private lateinit var mAdapter: MatchAdapter
    private var mEvent = ArrayList<Event>()

    fun getInstance(): FavoriteMatchFragment {
        if (fragment == null) {
            fragment = FavoriteMatchFragment()
        }

        return fragment as FavoriteMatchFragment
    }

    override fun layout(): Int {
        return R.layout.fragment_match_favorite
    }

    override fun onFragmentCreated() {
        initAdapter()
        binding.srlMatch.setOnRefreshListener(this)

    }

    override fun onFragmentClick() {

    }

    private fun initAdapter() {
        val glmanager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        mAdapter = MatchAdapter(mEvent, "favorite")
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
        requireActivity().database.use {
            mAdapter.clear()
            val result = select(FavoriteMatch.TABLE_MATCH)
            val favorite = result.parseList(classParser<FavoriteMatch>())

            favorite.forEach { data ->
                val match = Gson().fromJson(data.gson, Event::class.java)
                mAdapter.addItem(match)
            }

            if (mAdapter.getData().isEmpty()) {
                binding.tvNoData.visibility = View.VISIBLE
            } else {
                binding.tvNoData.visibility = View.GONE
            }
        }
    }

}
