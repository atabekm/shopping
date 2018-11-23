package com.example.shopping.di.module

import com.example.shopping.domain.usecase.cart.DecreaseCountUseCase
import com.example.shopping.domain.usecase.cart.GetCartProductsUseCase
import com.example.shopping.domain.usecase.cart.IncreaseCountUseCase
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
        increaseCountUseCase: IncreaseCountUseCase,
        getCartProductsUseCase: GetCartProductsUseCase
    ) = MainPresenter(getProductsUseCase, increaseCountUseCase, getCartProductsUseCase)

    @Singleton
    @Provides
    fun providesCartPresenter(
        increaseCountUseCase: IncreaseCountUseCase,
        decreaseCountUseCase: DecreaseCountUseCase,
        removeFromCartUseCase: RemoveFromCartUseCase,
        getCartProductsUseCase: GetCartProductsUseCase
    ) = CartPresenter(increaseCountUseCase, decreaseCountUseCase, removeFromCartUseCase, getCartProductsUseCase)

}