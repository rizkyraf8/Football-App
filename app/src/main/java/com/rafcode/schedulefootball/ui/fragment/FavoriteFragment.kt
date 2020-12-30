package com.rafcode.schedulefootball.ui.fragment

import androidx.viewpager.widget.ViewPager
import com.rafcode.schedulefootball.R
import com.rafcode.schedulefootball.databinding.FragmentMatchLayoutBinding
import com.rafcode.schedulefootball.utils.ViewPagerAdapter

class FavoriteFragment : BaseFragment<FragmentMatchLayoutBinding>() {

    object Companion {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    override fun layout(): Int {
        return R.layout.fragment_match_layout
    }

    override fun onFragmentCreated() {
        setupViewPager()
        binding.tlMenu.setupWithViewPager(binding.vpLayout)
    }

    override fun onFragmentClick() {

    }

    private fun setupViewPager() {

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(FavoriteMatchFragment().getInstance(), "Match")
        adapter.addFragment(FavoriteTeamFragment().getInstance(), "Team")
        binding.vpLayout.adapter = adapter
        binding.vpLayout.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
//                val fragment = adapter.instantiateItem(binding.vpLayout, position) as Fragment
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }
}
