package com.rafcode.schedulefootball.ui.fragment

import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.api.response.Team
import com.rafcode.schedulefootball.databinding.FragmentOverviewBinding

open class TeamOverviewFragment(private val team: Team?) : BaseFragment<FragmentOverviewBinding>() {

    private var fragment: TeamOverviewFragment? = null

    fun getInstance(): TeamOverviewFragment {
        if (fragment == null) {
            fragment = TeamOverviewFragment(team)
        }
        return fragment as TeamOverviewFragment
    }

    override fun onFragmentCreated() {
        binding.team = team
    }

    override fun onFragmentClick() {

    }

    override fun layout(): Int {
        return R.layout.fragment_overview
    }
}
