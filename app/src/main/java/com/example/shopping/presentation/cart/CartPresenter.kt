package com.example.shopping.presentation.cart

import com.example.shopping.di.SchedulerProvider
import com.example.shopping.domain.model.Cart
import com.example.shopping.domain.usecase.cart.*
import com.example.shopping.presentation.BasePresenter
import com.example.shopping.presentation.toCurrency
import io.reactivex.disposables.CompositeDisposable

class CartPresenter(
    private val increaseCountUseCase: IncreaseCountUseCase,
    private val decreaseCountUseCase: DecreaseCountUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val getCartProductsUseCase: GetCartProductsUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val scheduler: SchedulerProvider
) : BasePresenter<CartView> {
    private var view: CartView? = null
    private val subscription = CompositeDisposable()

    override fun attach(view: CartView) {
        this.view = view
    }

    override fun detach() {
        this.view = null

        subscription.clear()
    }

    fun subscribeToCartChanges() {
        subscription.add(getCartProductsUseCase.execute()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe(this::updateData, this::error))
    }

    fun increaseProductCount(cart: Cart) {
        increaseCountUseCase.execute(cart.product)
    }

    fun decreaseProductCount(cart: Cart) {
        decreaseCountUseCase.execute(cart.product)
    }

    fun removeFromCart(cart: Cart) {
        removeFromCartUseCase.execute(cart.product)
    }

    fun initiatePurchase() {
        clearCartUseCase.execute()
        view?.close()
        view?.showMessage("Purchased successfully")
    }

    private fun updateData(carts: List<Cart>) {
        updateCart(carts)
        updatePrice(carts)
        updateCount(carts)
    }

    private fun updateCart(carts: List<Cart>) {
        view?.updateCart(carts)
    }

    private fun updatePrice(carts: List<Cart>) {
        var totalPrice = 0f
        for (cart in carts) {
            val price = cart.count * cart.product.getNewPrice()
            totalPrice += price
        }

        view?.updateTotalPrice(totalPrice.toCurrency())
    }

    private fun updateCount(carts: List<Cart>) {
        val count = carts.sumBy { it.count }
        view?.updateButtonEnabled(count > 0)
        view?.updateListVisibility(count > 0)
    }

    private fun error(throwable: Throwable) {
        view?.showMessage("Error retrieving cart: ${throwable.localizedMessage}")
    }

}