package com.example.shopmobile.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.shopmobile.R
import com.example.shopmobile.utils.assertText
import com.example.shopmobile.utils.assertVisible
import org.hamcrest.Matchers.allOf

@RequiresApi(Build.VERSION_CODES.KITKAT)
class CartPage : AbstractBasePage() {

    fun verifyItemInCart(name: String, quantity: String) {
        onView(withText(name))
            .assertVisible("Cart item with name '$name' should be visible")

        // Use allOf to find the specific quantity TextView
        onView(allOf(withId(R.id.cart_item_quantity), withText(quantity)))
            .assertVisible("Cart item quantity '$quantity' should be visible")
    }

    fun verifyTotal(total: String) {
        onView(withId(R.id.cart_total_price))
            .assertText(total, "Cart Total Price mismatch")
    }

    fun verifyEmpty() {
        onView(withId(R.id.empty_cart_view))
            .assertVisible("Empty Cart View should be visible")
    }

    fun clickCheckout() {
        onView(withId(R.id.btn_checkout)).perform(click())
    }

    // New methods for interaction
    fun clickIncrement() {
        onView(withId(R.id.btn_increase)).perform(click())
    }

    fun clickDecrement() {
        onView(withId(R.id.btn_decrease)).perform(click())
    }

    fun clickRemove() {
        onView(withId(R.id.btn_remove)).perform(click())
    }
}