package com.rafcode.schedulefootball

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.rafcode.schedulefootball.R.id.bnMenu
import com.rafcode.schedulefootball.R.id.spinner
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