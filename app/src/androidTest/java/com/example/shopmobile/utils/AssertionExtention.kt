package com.example.shopmobile.utils

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.containsString
import org.hamcrest.StringDescription

/**
 * Adds context-rich, debuggable assertion messages to Espresso checks.
 */
@RequiresApi(Build.VERSION_CODES.KITKAT)
fun ViewInteraction.assertMatches(
    matcher: Matcher<View>,
    message: String
): ViewInteraction {

    return this.check { view, noViewFoundException ->

        if (noViewFoundException != null) {
            throw AssertionError(
                "$message\n View was not found in the view hierarchy.",
                noViewFoundException
            )
        }

        if (!matcher.matches(view)) {
            val expected = StringDescription().apply {
                matcher.describeTo(this)
            }

            val actual = StringDescription().apply {
                matcher.describeMismatch(view, this)
            }

            throw AssertionError(
                """
                $message
                  Assertion failed

                Expected:
                  $expected

                But was:
                  $actual

                View:
                  ${view.javaClass.simpleName} (${view.id})
                """.trimIndent()
            )
        }
    }
}

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun ViewInteraction.assertMatches(matcher: Matcher<View>): ViewInteraction =
        assertMatches(matcher, "View assertion failed")

    /**
     * Semantic alias for verifying visibility.
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun ViewInteraction.assertVisible(message: String = "View should be visible"): ViewInteraction =
        assertMatches(ViewMatchers.isDisplayed(), message)

    /**
     * Semantic alias for verifying a view is NOT visible.
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun ViewInteraction.assertNotVisible(message: String = "View should NOT be visible"): ViewInteraction =
        assertMatches(Matchers.not(ViewMatchers.isDisplayed()), message)

    /**
     * Semantic alias for verifying text content.
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun ViewInteraction.assertText(text: String, message: String = "Text mismatch"): ViewInteraction =
        assertMatches(ViewMatchers.withText(text), message)
