package com.example.shopmobile.tests

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shopmobile.common.BaseTest
import com.example.shopmobile.common.TestConfig
import com.example.shopmobile.annotations.ProductFeatureTest
import com.example.shopmobile.annotations.RegressionTest
import com.example.shopmobile.annotations.RequiresMockServer
import com.example.shopmobile.annotations.SmokeTest
import com.example.shopmobile.pages.ProductListPage
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ProductFeatureTest
class ProductListTest : BaseTest() {


    @Test
    @SmokeTest
    fun testProductsDisplayCorrectly() {
        listPage.verifyListVisible()
        listPage.verifyProductContent(0, TestConfig.FIRST_PRODUCT_TITLE)
    }


    @Test
    @SmokeTest
    @RequiresMockServer
    fun testServerErrorDisplaysErrorMessage() {

        configureMockError(500)

        // Act: Relaunch app to trigger the failed network call
        relaunchApp()

        // Assert
        listPage.verifyError("Failed to load products. Server returned code: 500")
    }
}