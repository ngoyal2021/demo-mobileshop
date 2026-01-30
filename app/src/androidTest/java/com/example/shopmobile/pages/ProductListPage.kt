package com.example.shopmobile.pages

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.shopmobile.R
import com.example.shopmobile.adapter.ProductAdapter
import com.example.shopmobile.utils.assertNotVisible
import com.example.shopmobile.utils.assertText
import com.example.shopmobile.utils.assertVisible
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

@RequiresApi(Build.VERSION_CODES.KITKAT)
class ProductListPage : AbstractBasePage() {

    fun verifyListVisible() {
        onView(withId(R.id.recycler_view))
            .assertVisible("Product List (RecyclerView) should be visible")

        onView(withId(R.id.error_layout))
            .assertNotVisible("Error Layout should NOT be visible")
    }

    fun verifyProductContent(position: Int, title: String) {
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<ProductAdapter.ProductViewHolder>(position))
            .check(matches(atPosition(position, hasDescendant(withText(title)))))
    }

    fun clickProduct(position: Int) {
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ProductAdapter.ProductViewHolder>(position, click()))
    }

    fun verifyError(message: String) {
        onView(withId(R.id.error_layout))
            .assertVisible("Error Layout should be visible")

        onView(withId(R.id.error_text))
            .assertText(message, "Error message text mismatch")
    }

    /**
     * Custom Matcher to safely match a view at a specific position in a RecyclerView.
     * This avoids ambiguous matches when multiple rows are visible.
     */
    private fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                if (view !is RecyclerView) return false
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: return false // No view holder at this position (scrolling might be needed)
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }
}