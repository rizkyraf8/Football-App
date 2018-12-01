package com.rafcode.schedulefootball.utils

import android.app.Activity
import android.content.Context

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.rafcode.schedulefootball.R


class NavigationBottom(internal var context: Context, bottomNavigation: AHBottomNavigation) {

    var bottomNavigation: AHBottomNavigation
        internal set

    init {
        this.bottomNavigation = bottomNavigation
    }

    fun setBottomNavigation(mOnNavigationItemSelectedListener: AHBottomNavigation.OnTabSelectedListener,
                            Selected: Int) {

        val tabColors = context.resources.getColor(R.color.white)
        val navigationAdapter = AHBottomNavigationAdapter(context as Activity, R.menu.menu_main)
        navigationAdapter.setupWithBottomNavigation(bottomNavigation, intArrayOf(tabColors))

        // Set background color
        bottomNavigation.defaultBackgroundColor = context.resources.getColor(R.color.white)

        // Disable the translation inside the CoordinatorLayout
        bottomNavigation.isBehaviorTranslationEnabled = false

        // Change colors
        // bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.accentColor = context.resources.getColor(R.color.colorAccent)
        bottomNavigation.isForceTint = true
        bottomNavigation.isTranslucentNavigationEnabled = false

        // Set current item programmatically

        //        bottomNavigation.setNotification("5", 4);
        bottomNavigation.setCurrentItem(Selected, false)
        bottomNavigation.setOnTabSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
    }
}
