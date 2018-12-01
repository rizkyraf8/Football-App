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
import com.rafcode.schedulefootball.api.response.Event
import com.rafcode.schedulefootball.ui.adapter.MatchAdapter
import com.rafcode.schedulefootball.utils.database
import com.rafcode.schedulefootball.utils.database.FavoriteMatch
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

open class FavoriteMatchFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var fragment: FavoriteMatchFragment? = null
    lateinit var mAdapter: MatchAdapter
    var mEvent = ArrayList<Event>()


    fun getInstance(): FavoriteMatchFragment {
        if (fragment == null) {
            fragment = FavoriteMatchFragment()
        }

        return fragment as FavoriteMatchFragment
    }

    fun clearInstance() {
        if (fragment != null) fragment = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        srlMatch.setOnRefreshListener(this)

        val glmanager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mAdapter = MatchAdapter(activity!!, mEvent, "favorite")
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
            val result = select(FavoriteMatch.TABLE_MATCH)
            val favorite = result.parseList(classParser<FavoriteMatch>())

            favorite.forEach { data ->
                var match = Gson().fromJson(data.gson, Event::class.java)
                mAdapter.reCreate(match)
            }

        }
    }

}
