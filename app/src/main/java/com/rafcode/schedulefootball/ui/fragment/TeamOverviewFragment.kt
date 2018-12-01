package com.rafcode.schedulefootball.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Player
import com.rafcode.schedulefootball.ui.adapter.PlayerAdapter
import com.rafcode.schedulefootball.ui.presenter.PlayerPresenter
import com.rafcode.schedulefootball.utils.TempData
import kotlinx.android.synthetic.main.fragment_overview.*

open class TeamOverviewFragment : Fragment() {

    private var fragment: TeamOverviewFragment? = null

    fun getInstance(): TeamOverviewFragment {
        if (fragment == null) {
            fragment = TeamOverviewFragment()
        }
        return fragment as TeamOverviewFragment
    }

    fun clearInstance() {
        if (fragment != null) fragment = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvDesc.text = TempData.teams?.strDescriptionEN

    }


}
