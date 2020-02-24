package com.sample.android.smallcount

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.widget.EditText
import android.widget.TextView
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Rule
import org.junit.Test

@LargeTest
class MainActivityTest {

  @Rule
  @JvmField
  var rule = ActivityTestRule(MainActivity::class.java)

  @Test
  fun tappingOnTitleOpensEditDialog() {
    onView(withId(R.id.textVictoryTitle))
        .perform(click())

    onView(withId(R.id.alertTitle))
        .check(matches(isDisplayed()))

    onView(withId(android.R.id.button2))
        .perform(click())
  }

  @Test
  fun editingDialogUpdatesTitle() {
    onView(withId(R.id.textVictoryTitle))
        .perform(click())

    val newTitle = "Made the bed"
    onView(instanceOf(EditText::class.java))
        .perform(clearText())
        .perform(typeText(newTitle))

    onView(withText(R.string.dialog_ok))
        .perform(click())

    onView(allOf(withId(R.id.textVictoryTitle), withText(newTitle)))
        .check(matches(isDisplayed()))
  }

  @Test
  fun incrementingVictoryCountUpdatesCountView() {
    // 1
    val previousCountString = rule.activity.findViewById<TextView>(R.id.textVictoryCount).text.toString()
    val previousCount = if (previousCountString.isBlank()) 0 else previousCountString.toInt()

    // 2
    onView(withId(R.id.fab))
            .perform(click())

    // 3
    onView(allOf(withId(R.id.textVictoryCount),
            withText((previousCount + 1).toString())))
            .check(matches(isDisplayed()))
  }

  @Test
  fun editingTitleDoesntChangeCount() {
    // 1
    onView(withId(R.id.fab))
            .perform(click())
    // 2
    onView(withId(R.id.textVictoryTitle))
            .perform(click())
    val newTitle = "Made the bed"
    onView(instanceOf(EditText::class.java))
            .perform(clearText())
            .perform(typeText(newTitle))
    onView(withText(R.string.dialog_ok))
            .perform(click())

    // 3
    onView(allOf(withId(R.id.textVictoryCount), withText("0")))
            .check(doesNotExist())
  }



}
