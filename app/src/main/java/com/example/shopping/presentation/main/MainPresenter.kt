package com.example.shopping.presentation.main

import com.example.shopping.domain.model.Cart
import com.example.shopping.domain.model.Product
import com.example.shopping.domain.usecase.cart.ClearCartUseCase
import com.example.shopping.domain.usecase.cart.GetCartProductsUseCase
import com.example.shopping.domain.usecase.cart.IncreaseCountUseCase
import com.example.shopping.domain.usecase.product.GetProductsUseCase
import com.example.shopping.presentation.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(
    private val getProductsUseCase: GetProductsUseCase,
    private val increaseCountUseCase: IncreaseCountUseCase,
    private val getCartProductsUseCase: GetCartProductsUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : BasePresenter<MainView> {
    private var view: MainView? = null
    private val subscription = CompositeDisposable()

    override fun attach(view: MainView) {
        this.view = view

        subscription.add(
            getCartProductsUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::observeCart, this::failedToRetrieve)
        )
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
            getProductsUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::returnProducts, this::failedToRetrieve)
        )
    }

    private fun returnProducts(products: List<Product>) {
        view?.updateProducts(products)
    }

    private fun failedToRetrieve(throwable: Throwable) {
        view?.errorRetrieving(throwable.message)
    }

    private fun observeCart(carts: List<Cart>) {
        view?.updateCartCount(carts.size)
    }

}