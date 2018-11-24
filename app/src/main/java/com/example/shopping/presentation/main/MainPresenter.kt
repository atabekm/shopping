package com.example.shopping.presentation.main

import com.example.shopping.di.SchedulerProvider
import com.example.shopping.domain.model.Cart
import com.example.shopping.domain.model.Product
import com.example.shopping.domain.usecase.cart.ClearCartUseCase
import com.example.shopping.domain.usecase.cart.GetCartProductsUseCase
import com.example.shopping.domain.usecase.cart.IncreaseCountUseCase
import com.example.shopping.domain.usecase.product.GetProductsUseCase
import com.example.shopping.presentation.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables

class MainPresenter(
    private val getProductsUseCase: GetProductsUseCase,
    private val increaseCountUseCase: IncreaseCountUseCase,
    private val getCartProductsUseCase: GetCartProductsUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val scheduler: SchedulerProvider
) : BasePresenter<MainView> {
    private var view: MainView? = null
    private val subscription = CompositeDisposable()

    override fun attach(view: MainView) {
        this.view = view
    }

    override fun detach() {
        this.view = null

        subscription.clear()
    }

    fun clearCarts() {
        clearCartUseCase.execute()
    }

    fun addToCart(product: Product) {
        increaseCountUseCase.execute(product)
    }

    fun fetchProducts() {
        subscription.add(
            Observables.combineLatest(getProductsUseCase.execute().toObservable(), getCartProductsUseCase.execute())
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(this::returnProducts, this::failedToRetrieve)
        )
    }

    private fun returnProducts(pair: Pair<List<Product>, List<Cart>>) {
        view?.updateProducts(pair.first, pair.second.map { it.product.id }.toList())
        view?.updateCartCount(pair.second.size)
    }

    private fun failedToRetrieve(throwable: Throwable) {
        view?.errorRetrieving(throwable.message)
    }

}