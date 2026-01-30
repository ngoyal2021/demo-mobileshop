package com.example.shopmobile.tests

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shopmobile.annotations.CartFeatureTest
import com.example.shopmobile.common.BaseTest
import com.example.shopmobile.common.TestConfig
import com.example.shopmobile.annotations.RegressionTest
import com.example.shopmobile.annotations.SmokeTest
import com.example.shopmobile.pages.CartPage
import com.example.shopmobile.pages.CheckoutPage
import com.example.shopmobile.pages.ConfirmationPage
import com.example.shopmobile.pages.ProductDetailPage
import com.example.shopmobile.pages.ProductListPage
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@CartFeatureTest
class CartFlowTest : BaseTest() {

    @Test
    @SmokeTest
    fun testAddToCartFlow() {
        // 1. Select Product from list
        listPage.clickProduct(0)

        // 2. Verify Details and Add to Cart
        detailPage.verifyProductDetails(TestConfig.FIRST_PRODUCT_TITLE, TestConfig.FIRST_PRODUCT_PRICE)
        detailPage.addToCart()

        // 3. Navigate to Cart (using standard menu action)
        detailPage.navigateToCart()

        // 4. Verify Item in Cart
        cartPage.verifyItemInCart(TestConfig.FIRST_PRODUCT_TITLE, "1")
        cartPage.verifyTotal(TestConfig.FIRST_PRODUCT_PRICE)
    }

    @Test
    @RegressionTest
    fun testEmptyCartState() {

        // Use the inherited method 'navigateToCart' instead of 'clickCart'
        listPage.navigateToCart()
        cartPage.verifyEmpty()
    }

    @Test
    @RegressionTest
    fun testCartItemInteractions() {

        // 1. Add item
        listPage.clickProduct(0)
        detailPage.addToCart()
        detailPage.clickCartFab()

        // 2. Increment Quantity
        cartPage.clickIncrement()
        cartPage.verifyItemInCart(TestConfig.FIRST_PRODUCT_TITLE, "2")
        // Price should be doubled: 109.95 * 2 = 219.90
        cartPage.verifyTotal("$219.90")

        // 3. Decrement Quantity
        cartPage.clickDecrement()
        cartPage.verifyItemInCart(TestConfig.FIRST_PRODUCT_TITLE, "1")
        cartPage.verifyTotal(TestConfig.FIRST_PRODUCT_PRICE)

        // 4. Remove Item
        cartPage.clickRemove()
        cartPage.verifyEmpty()
    }

    @Test
    @SmokeTest
    fun testCompletePurchaseFlow() {

        // 1. Add item to cart
        listPage.clickProduct(0)
        detailPage.addToCart()

        // 2. Use FAB to navigate to Cart (Testing new UI element)
        detailPage.clickCartFab()

        // 3. Proceed to Checkout
        cartPage.clickCheckout()
        checkoutPage.verifyScreenVisible()
        checkoutPage.verifyOrderSummary(1, TestConfig.FIRST_PRODUCT_PRICE)

        // 4. Confirm Order
        checkoutPage.clickConfirmOrder()
        confirmationPage.verifyConfirmationDisplayed()

        // 5. Return to Home
        confirmationPage.clickContinueShopping()
        listPage.verifyListVisible()

        // 6. Verify Cart is now empty
        listPage.clickCartFab()
        cartPage.verifyEmpty()
    }
}