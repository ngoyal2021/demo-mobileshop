package com.example.shopmobile.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.shopmobile.R
import org.hamcrest.Matchers.containsString

/**
 * AbstractBasePage contains interactions for UI elements visible on multiple screens,
 * such as the Toolbar, Options Menu (Cart), or common Dialogs.
 */

@RequiresApi(Build.VERSION_CODES.KITKAT)
abstract class AbstractBasePage {

    /**
     * Common action: Clicking the Cart icon in the ActionBar/Toolbar.
     * Available on List and Detail pages.
     */
    fun navigateToCart() {
        onView(withId(R.id.action_cart)).perform(click())
    }

    /**
     * Common action: Clicking the Cart Floating Action Button (FAB).
     * Available on List and Detail pages.
     */
    fun clickCartFab() {
        onView(withId(R.id.fab_cart)).perform(click())
    }


}