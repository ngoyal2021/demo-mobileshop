package com.example.shopmobile.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.shopmobile.R
import com.example.shopmobile.utils.assertText
import org.hamcrest.Matchers.containsString

@RequiresApi(Build.VERSION_CODES.KITKAT)
class ProductDetailPage : AbstractBasePage() {

    fun verifyProductDetails(name: String, price: String) {
        onView(withId(R.id.detail_name))
            .assertText(name, "Product Name mismatch")

        onView(withId(R.id.detail_price))
            .assertText(price, "Product Price mismatch")
    }


    fun verifyDescriptionContains(text: String) {
        // Description is often below the fold, so we must scroll to it
        onView(withId(R.id.detail_description))
            .perform(scrollTo())
            .check(matches(withText(containsString(text))))
    }

    fun addToCart() {
        onView(withId(R.id.btn_add_to_cart)).perform(click())
    }

}