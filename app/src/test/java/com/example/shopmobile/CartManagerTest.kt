package com.example.shopmobile

import android.annotation.SuppressLint
import com.example.shopmobile.model.Product
import com.example.shopmobile.util.CartManager
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Constructor

class CartManagerTest {

    private lateinit var cartManager: CartManager

    @SuppressLint("VisibleForTests")
    @Before
    fun setUp() {
        CartManager.resetInstance()
        cartManager = CartManager.getInstance()
    }

    private fun createProduct(id: String, price: Double): Product {
        return Product(id, "Test Product", price, "Desc", "Cat", "url", Product.Rating(5.0, 1))
    }

    @Test
    fun testAddItem() {
        val product = createProduct("1", 100.0)
        cartManager.addItem(product)
        
        assertEquals(1, cartManager.items.size)
        assertEquals(1, cartManager.items[0].quantity)
    }

    @Test
    fun testAddDuplicateItemIncrementsQuantity() {
        val product = createProduct("1", 100.0)
        cartManager.addItem(product)
        cartManager.addItem(product)
        
        assertEquals(1, cartManager.items.size)
        assertEquals(2, cartManager.items[0].quantity)
    }

    @Test
    fun testTotalPriceCalculation() {
        cartManager.addItem(createProduct("1", 10.0)) // Qty 1, $10
        cartManager.addItem(createProduct("1", 10.0)) // Qty 2, $20
        cartManager.addItem(createProduct("2", 30.0)) // Qty 1, $30
        
        // Total should be 50.0
        assertEquals(50.0, cartManager.totalPrice, 0.01)
    }

    @Test
    fun testRemoveItem() {
        val product = createProduct("1", 10.0)
        cartManager.addItem(product)
        val item = cartManager.items[0]
        
        cartManager.removeItem(item)
        
        assertEquals(0, cartManager.items.size)
    }
}