package com.rafcode.schedulefootball.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.utils.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_match_layout.*


class MatchFragment : Fragment() {

    private var fragment: MatchFragment? = null

    fun newInstance(): MatchFragment {
        if (fragment == null) {
            fragment = MatchFragment()
        }
        return fragment as MatchFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        tlMenu.setupWithViewPager(vpLayout)
    }

    private fun setupViewPager() {

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(NextMatchFragment().getInstance(), "Next")
        adapter.addFragment(LastMatchFragment().getInstance(), "Last")
        vpLayout.adapter = adapter
        vpLayout.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                val fragment = adapter.instantiateItem(vpLayout, position) as Fragment
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }
}
