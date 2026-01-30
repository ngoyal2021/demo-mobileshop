package com.example.shopmobile.pages


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.shopmobile.R
import com.example.shopmobile.utils.assertText
import com.example.shopmobile.utils.assertVisible

@RequiresApi(Build.VERSION_CODES.KITKAT)
class CheckoutPage : AbstractBasePage() {

    fun verifyScreenVisible() {
        onView(withText("Checkout"))
            .assertVisible("Checkout Title should be visible")

        onView(withId(R.id.btn_confirm_order))
            .assertVisible("Confirm Order button should be visible")
    }

    fun verifyOrderSummary(count: Int, total: String) {
        onView(withId(R.id.summary_items_count))
            .assertText("Items ($count)", "Order Summary Item Count mismatch")

        onView(withId(R.id.summary_total))
            .assertText(total, "Order Summary Total Price mismatch")
    }

    fun clickConfirmOrder() {
        onView(withId(R.id.btn_confirm_order)).perform(click())
    }
}