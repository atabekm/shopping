package com.example.shopping.presentation.main

import com.example.shopping.TestSchedulers
import com.example.shopping.domain.model.Cart
import com.example.shopping.domain.model.Product
import com.example.shopping.domain.usecase.cart.ClearCartUseCase
import com.example.shopping.domain.usecase.cart.GetCartProductsUseCase
import com.example.shopping.domain.usecase.cart.IncreaseCountUseCase
import com.example.shopping.domain.usecase.product.GetProductsUseCase
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class MainPresenterTest {
    private lateinit var presenter: MainPresenter
    private val getProductsUseCase = mock<GetProductsUseCase>()
    private val increaseCountUseCase = mock<IncreaseCountUseCase>()
    private val getCartProductsUseCase = mock<GetCartProductsUseCase>()
    private val clearCartUseCase = mock<ClearCartUseCase>()
    private val scheduler = TestSchedulers()
    private val view = mock<MainView>()
    private val product = Product(1, "title", "description", 123f)
    private val productList = listOf(product)
    private val cart = Cart(1, product)
    private val cartList = listOf(cart)
    private val pair = Pair(productList, cartList)
    private val throwable = Throwable("Error")
    private val singleProductList = Single.just(productList)
    private val observableCartList = Observable.just(cartList)
    private val error = Observable.error<List<Cart>>(throwable)

    @Before
    fun setUp() {
        presenter = MainPresenter(getProductsUseCase, increaseCountUseCase, getCartProductsUseCase, clearCartUseCase, scheduler)
        presenter.attach(view)
    }

    @Test
    fun testClearCarts() {
        presenter.clearCarts()

        verify(clearCartUseCase, times(1)).execute()
    }

    @Test
    fun testAddToCart() {
        presenter.addToCart(product)

        verify(increaseCountUseCase, times(1)).execute(product)
    }

    @Test
    fun testFetchProductsSuccess() {
        whenever(getProductsUseCase.execute()).thenReturn(singleProductList)
        whenever(getCartProductsUseCase.execute()).thenReturn(observableCartList)

        presenter.fetchProducts()

        verify(view, times(1)).updateProducts(pair.first, pair.second.map { it.product.id }.toList())
        verify(view, times(1)).updateCartCount(pair.second.size)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun testFetchProductsError() {
        whenever(getProductsUseCase.execute()).thenReturn(singleProductList)
        whenever(getCartProductsUseCase.execute()).thenReturn(error)

        presenter.fetchProducts()

        verify(view, times(1)).errorRetrieving(throwable.message)
        verifyNoMoreInteractions(view)
    }
}