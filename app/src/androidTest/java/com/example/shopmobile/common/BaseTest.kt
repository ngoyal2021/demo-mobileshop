package com.example.shopmobile.common

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.shopmobile.MainActivity
import com.example.shopmobile.api.NetworkClient
import com.example.shopmobile.pages.CartPage
import com.example.shopmobile.pages.CheckoutPage
import com.example.shopmobile.pages.ConfirmationPage
import com.example.shopmobile.pages.ProductDetailPage
import com.example.shopmobile.pages.ProductListPage
import com.example.shopmobile.util.CartManager
import com.example.shopmobile.util.EspressoIdlingResource
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.io.IOException

/**
 * BaseTest handles the setup and teardown of the test environment.
 * It manages the MockWebServer lifecycle and ensures the NetworkClient
 * is pointing to the correct endpoint (Localhost or Real/Stub).
 */
open class BaseTest {

    // @Rule ensures this rule runs around every test method.
    // 3rd parameter 'false' disables automatic activity launch.
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    // New Rule: Automatically skips tests annotated with @RequiresMockServer if running in Real mode
    @get:Rule
    val mockServerRule = MockServerRule()

    // Nullable to handle cases where MockServer is not used
    protected var mockWebServer: MockWebServer? = null

    protected var scenario: ActivityScenario<MainActivity>? = null

    // Instantiate all page objects here
    protected val listPage = ProductListPage()
    protected val detailPage = ProductDetailPage()
    protected val cartPage = CartPage()
    protected val checkoutPage = CheckoutPage()
    protected val confirmationPage = ConfirmationPage()


    @Before
    open fun setUp() {
        // 1. Reset Singleton State (Cart) to ensure test isolation
        resetCart()

        // 2. Configure Networking and Launch Strategy
        if (TestConfig.USE_MOCK_SERVER) {
            configureMockServer()
            // In Mock Mode, we now automatically launch the app with the default data.
            // This avoids the need for manual stubbing in every happy-path test.
            launchAppIfNeeded()
        } else {
            configureRealClient()
            launchAppIfNeeded()
        }

        // 3. Register Idling Resources for Espresso async handling
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    private fun configureMockServer() {
        val server = MockWebServer()
        try {
            // Set a Default Dispatcher that always serves the happy path (Product List).
            // This mimics the manual run behavior.
            server.dispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return MockResponse()
                        .setResponseCode(200)
                        .setBody(TestConfig.MOCK_PRODUCTS_JSON)
                }
            }

            // Start on an available port (0 = system picks ephemeral port)
            server.start()
            mockWebServer = server

            // Inject the dynamic localhost URL into the NetworkClient
            val baseUrl = server.url("/").toString()
            NetworkClient.setBaseUrl(baseUrl)
        } catch (e: IOException) {
            throw RuntimeException("Failed to start MockWebServer", e)
        }
    }

    private fun configureRealClient() {
        // Reset NetworkClient to use its default internal URL/Logic
        NetworkClient.resetClient()
    }

    @After
    open fun tearDown() {
        // ActivityTestRule handles activity finish automatically,
        // but we must shut down our server and unregister resources.

        // Shutdown MockWebServer if it was started
        try {
            mockWebServer?.shutdown()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Unregister Idling Resource
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    /**
     * Helper to override the default dispatcher with an error response.
     * Use this for testing failure scenarios.
     * Note: You must call relaunchApp() after this to trigger the new request.
     */
    protected fun configureMockError(responseCode: Int) {
        if (TestConfig.USE_MOCK_SERVER) {
            mockWebServer?.dispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return MockResponse()
                        .setResponseCode(responseCode)
                        .setBody("Error")
                }
            }
        }
    }

    protected fun launchApp() {
        if (scenario == null) {
            val intent = Intent(
                androidx.test.platform.app.InstrumentationRegistry
                    .getInstrumentation().targetContext,
                MainActivity::class.java
            ).apply {
                putExtra("IS_TEST_RUN", true)
            }

            scenario = ActivityScenario.launch(intent)
        }
    }

    protected fun relaunchApp() {
        scenario?.close()
        scenario = null
        launchApp()
    }

    protected fun launchAppIfNeeded() {
        if (scenario == null) {
            launchApp()
        }
    }


    private fun resetCart() {
        CartManager.resetInstance();
    }
}