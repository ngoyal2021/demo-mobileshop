package com.example.shopmobile.tests


import androidx.test.ext.junit.runners.AndroidJUnit4

import com.example.shopmobile.common.BaseTest
import com.example.shopmobile.common.TestConfig

import com.example.shopmobile.annotations.ProductDetailsFeatureTest
import com.example.shopmobile.annotations.RegressionTest
import com.example.shopmobile.pages.ProductDetailPage
import com.example.shopmobile.pages.ProductListPage
import com.example.shopmobile.utils.CommonMethods
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ProductDetailsFeatureTest

class DetailFlowTest : BaseTest() {


    @Test
    @RegressionTest
    fun testProductDetailsContent() {

        // Open first product
        listPage.clickProduct(0)

        // Verify standard fields
        detailPage.verifyProductDetails(TestConfig.FIRST_PRODUCT_TITLE, TestConfig.FIRST_PRODUCT_PRICE)

        // Verify specific rich content fields
        // Rating: 3.9 (120 reviews) - Use Contains
        CommonMethods.verifyTextContains("3.9 (120 reviews)")

        // Category
        CommonMethods.verifyTextVisible("men's clothing")

        // Description snippet (part of it) - Use verifyDescriptionContains to handle scrolling
        detailPage.verifyDescriptionContains("Your perfect pack for everyday use")
    }
}