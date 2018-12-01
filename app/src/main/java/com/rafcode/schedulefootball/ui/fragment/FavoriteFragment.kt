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


class FavoriteFragment : Fragment() {

    private var fragment: FavoriteFragment? = null

    fun newInstance(): FavoriteFragment {
        if (fragment == null) {
            fragment = FavoriteFragment()
        }
        return fragment as FavoriteFragment
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
        adapter.addFragment(FavoriteMatchFragment().getInstance(), "Match")
        adapter.addFragment(FavoriteTeamFragment().getInstance(), "Team")
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
