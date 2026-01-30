package com.example.shopmobile.utils

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.containsString

object CommonMethods {

    /**
     * Common assertion: checking if a specific text is displayed on the screen.
     * Uses exact match.
     */
    fun verifyTextVisible(text: String) {
        onView(withText(text)).check(matches(isDisplayed()))
    }

    /**
     * Common assertion: checking if the visible text contains the provided substring.
     */
    fun verifyTextContains(text: String) {
        onView(withText(containsString(text))).check(matches(isDisplayed()))
    }
}