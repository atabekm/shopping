package com.example.shopping.di.module

import com.example.shopping.domain.usecase.cart.AddToCartUseCase
import com.example.shopping.domain.usecase.cart.GetCartProductsUseCase
import com.example.shopping.domain.usecase.cart.RemoveFromCartUseCase
import com.example.shopping.domain.usecase.product.GetProductsUseCase
import com.example.shopping.presentation.cart.CartPresenter
import com.example.shopping.presentation.main.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Singleton
    @Provides
    fun providesMainPresenter(
        getProductsUseCase: GetProductsUseCase,
        addToCartUseCase: AddToCartUseCase,
        getCartProductsUseCase: GetCartProductsUseCase
    ) = MainPresenter(getProductsUseCase, addToCartUseCase, getCartProductsUseCase)

    @Singleton
    @Provides
    fun providesCartPresenter(
        addToCartUseCase: AddToCartUseCase,
        removeFromCartUseCase: RemoveFromCartUseCase,
        getCartProductsUseCase: GetCartProductsUseCase
    ) = CartPresenter(addToCartUseCase, removeFromCartUseCase, getCartProductsUseCase)

}