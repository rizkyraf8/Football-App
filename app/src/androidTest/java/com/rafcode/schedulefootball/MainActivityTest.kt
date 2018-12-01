package com.rafcode.schedulefootball

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.rafcode.schedulefootball.R.id.*
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour() {
        onView(allOf(withId(bnMenu), isDisplayed()))
            .check(matches(isDisplayed()))
        onView(withText("Teams")).perform(click())

        onView(allOf(withId(spinner), isDisplayed())).check(matches(isDisplayed()))
        onView(withId(spinner)).perform(click())

        onView(allOf(withText("Italian Serie A"), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withText("Italian Serie A"), isDisplayed())).check(matches(isDisplayed())).perform(click())

        Thread.sleep(1500)

        onView(withText("Matchs")).perform(click())
        onView(withText("Last")).perform(click())

        onView(allOf(withId(spinner), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(spinner), isDisplayed())).check(matches(isDisplayed())).perform(click())

        onView(allOf(withText("Italian Serie A"), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withText("Italian Serie A"), isDisplayed())).check(matches(isDisplayed())).perform(click())

        Thread.sleep(1500)

        onView(allOf(withText("Favorite"))).perform(click())
        onView(allOf(withText("Team"))).perform(click())
    }
}