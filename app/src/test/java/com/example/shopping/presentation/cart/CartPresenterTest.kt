package com.example.shopping.presentation.cart

import com.example.shopping.TestSchedulers
import com.example.shopping.domain.model.Cart
import com.example.shopping.domain.model.Product
import com.example.shopping.domain.usecase.cart.*
import com.example.shopping.presentation.toCurrency
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class CartPresenterTest {
    private lateinit var presenter: CartPresenter
    private val increaseCountUseCase = mock<IncreaseCountUseCase>()
    private val decreaseCountUseCase = mock<DecreaseCountUseCase>()
    private val removeFromCartUseCase = mock<RemoveFromCartUseCase>()
    private val getCartProductsUseCase = mock<GetCartProductsUseCase>()
    private val clearCartUseCase = mock<ClearCartUseCase>()
    private val scheduler = TestSchedulers()
    private val view = mock<CartView>()
    private val product = Product(1, "title", "description", 123f)
    private val cart = Cart(1, product)
    private val cartList = listOf(cart)
    private val throwable = Throwable("Error")
    private val observableCartList = Observable.just(cartList)
    private val error = Observable.error<List<Cart>>(throwable)

    @Before
    fun setUp() {
        presenter = CartPresenter(increaseCountUseCase, decreaseCountUseCase, removeFromCartUseCase, getCartProductsUseCase, clearCartUseCase, scheduler)
        presenter.attach(view)
    }

    @Test
    fun testIncreaseProductCount() {
        presenter.increaseProductCount(cart)

        verify(increaseCountUseCase, times(1)).execute(cart.product)
    }

    @Test
    fun testDecreaseProductCount() {
        presenter.decreaseProductCount(cart)

        verify(decreaseCountUseCase, times(1)).execute(cart.product)
    }

    @Test
    fun testRemoveFromCart() {
        presenter.removeFromCart(cart)

        verify(removeFromCartUseCase, times(1)).execute(cart.product)
    }

    @Test
    fun testInitiatePurchase() {
        presenter.initiatePurchase()

        verify(clearCartUseCase, times(1)).execute()
        verify(view, times(1)).close()
        verify(view, times(1)).showMessage("Purchased successfully")
    }

    @Test
    fun testSubscribeToCartChangesSuccess() {
        whenever(getCartProductsUseCase.execute()).thenReturn(observableCartList)

        presenter.subscribeToCartChanges()

        verify(view, times(1)).updateCart(cartList)
        verify(view, times(1)).updateTotalPrice(product.price.toCurrency())
        verify(view, times(1)).updateButtonEnabled(cart.count > 0)
        verify(view, times(1)).updateListVisibility(cart.count > 0)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun testSubscribeToCartChangesError() {
        whenever(getCartProductsUseCase.execute()).thenReturn(error)

        presenter.subscribeToCartChanges()

        verify(view, times(1)).showMessage("Error retrieving cart: ${throwable.localizedMessage}")
        verifyNoMoreInteractions(view)
    }
}