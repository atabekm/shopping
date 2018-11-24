package com.example.shopping.data.repository

import com.example.shopping.domain.model.Cart
import com.example.shopping.domain.model.Product
import com.example.shopping.domain.repository.CartRepository
import org.junit.Before
import org.junit.Test

class CartRepositoryTest {
    private lateinit var repository: CartRepository

    private val product1 = Product(1, "title1", "description1", 123f)
    private val product2 = Product(2, "title2", "description2", 456f)
    private val carts = mutableListOf<Cart>()

    @Before
    fun setUp() {
        repository = CartRepositoryImpl()
    }

    @Test
    fun increaseProductCount_EmptyCart_OneCartAdded() {
        carts.add(0, Cart(1, product1))

        repository.increaseProductCount(product1)

        val test = repository.getProductsInCart().test()
        test.assertNoErrors()
        test.assertValue { carts == it }
    }

    @Test
    fun increaseProductCount_ProductExists_CartItemCountIncreased() {
        carts.add(0, Cart(2, product1))

        repository.increaseProductCount(product1)
        repository.increaseProductCount(product1)

        val test = repository.getProductsInCart().test()
        test.assertNoErrors()
        test.assertValue { carts == it }
    }

    @Test
    fun decreaseProductCount_EmptyCart_DoNothing() {
        repository.decreaseProductCount(product1)
        val test = repository.getProductsInCart().test()

        test.assertNoErrors()
        test.assertValue { carts == it }
    }

    @Test
    fun decreaseProductCount_OneProductExists_ProductRemovedFromCart() {
        repository.increaseProductCount(product1)

        repository.decreaseProductCount(product1)
        val test = repository.getProductsInCart().test()

        test.assertNoErrors()
        test.assertValue { carts == it }
    }

    @Test
    fun decreaseProductCount_TwoProductsExist_OneProductRemainedInCart() {
        carts.add(0, Cart(1, product1))
        repository.increaseProductCount(product1)
        repository.increaseProductCount(product1)

        repository.decreaseProductCount(product1)
        val test = repository.getProductsInCart().test()

        test.assertNoErrors()
        test.assertValue { carts == it }
    }

    @Test
    fun removeFromCart_TwoProductsExist_OneProductRemainedInCart() {
        carts.add(0, Cart(1, product1))
        repository.increaseProductCount(product1)
        repository.increaseProductCount(product2)

        repository.removeFromCart(product2)
        val test = repository.getProductsInCart().test()

        test.assertNoErrors()
        test.assertValue { carts == it }
    }

    @Test
    fun removeFromCart_OneProductExists_EmptyCart() {
        repository.increaseProductCount(product2)

        repository.removeFromCart(product2)
        val test = repository.getProductsInCart().test()

        test.assertNoErrors()
        test.assertValue { carts == it }
    }

    @Test
    fun clearCart_TwoProductExists_EmptyCart() {
        repository.increaseProductCount(product1)
        repository.increaseProductCount(product2)

        repository.clearCart()
        val test = repository.getProductsInCart().test()

        test.assertNoErrors()
        test.assertValue { carts == it }
    }


}