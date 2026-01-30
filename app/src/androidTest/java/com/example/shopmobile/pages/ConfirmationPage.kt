package com.example.shopmobile.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.shopmobile.R
import com.example.shopmobile.utils.assertVisible

@RequiresApi(Build.VERSION_CODES.KITKAT)
class ConfirmationPage : AbstractBasePage() {

    fun verifyConfirmationDisplayed() {
        onView(withText("Order Placed!"))
            .assertVisible("Order Placed title should be visible")

        onView(withText("Continue Shopping"))
            .assertVisible("Continue Shopping button should be visible")
    }

    fun clickContinueShopping() {
        onView(withId(R.id.btn_back_home)).perform(click())
    }
}