package com.example.shopping.presentation.cart

import com.example.shopping.domain.model.Cart
import com.example.shopping.domain.usecase.cart.AddToCartUseCase
import com.example.shopping.domain.usecase.cart.GetCartProductsUseCase
import com.example.shopping.domain.usecase.cart.RemoveFromCartUseCase
import com.example.shopping.presentation.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CartPresenter(
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val getCartProductsUseCase: GetCartProductsUseCase
) : BasePresenter<CartView> {
    private var view: CartView? = null
    private val subscription = CompositeDisposable()

    override fun attach(view: CartView) {
        this.view = view

        subscription.add(getCartProductsUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::updateCart, this::error))
    }

    override fun detach() {
        this.view = null

        subscription.clear()
    }

    private fun updateCart(carts: List<Cart>) {
        view?.updateCart(carts)
    }

    private fun error(throwable: Throwable) {
        view?.error(throwable.localizedMessage)
    }

}