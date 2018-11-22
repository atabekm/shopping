package com.example.shopping.presentation.main

import com.example.shopping.domain.model.Cart
import com.example.shopping.domain.model.Product
import com.example.shopping.domain.usecase.cart.AddToCartUseCase
import com.example.shopping.domain.usecase.cart.GetCartProductsUseCase
import com.example.shopping.domain.usecase.product.GetProductsUseCase
import com.example.shopping.presentation.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(
    private val getProductsUseCase: GetProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val getCartProductsUseCase: GetCartProductsUseCase
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

        subscription.dispose()
    }

    fun addToCart(product: Product) {
        addToCartUseCase.execute(product)
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