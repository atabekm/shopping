package com.example.shopping.presentation.main

import com.example.shopping.domain.model.Product
import com.example.shopping.domain.usecase.product.GetProductsUseCase
import com.example.shopping.presentation.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(private val getProductsUseCase: GetProductsUseCase): BasePresenter<MainView> {
    private var view: MainView? = null

    override fun attach(view: MainView) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }

    fun fetchProducts() {
        getProductsUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::returnProducts, this::failedToRetrieve)
    }

    private fun returnProducts(products: List<Product>) {
        view?.updateProducts(products)
    }

    private fun failedToRetrieve(throwable: Throwable) {
        view?.errorRetrieving(throwable.message)
    }

}