package com.example.shopping.di.module

import com.example.shopping.di.SchedulerProvider
import com.example.shopping.domain.usecase.cart.*
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
        getCartProductsUseCase: GetCartProductsUseCase,
        clearCartUseCase: ClearCartUseCase,
        scheduler: SchedulerProvider
    ) = MainPresenter(getProductsUseCase, increaseCountUseCase, getCartProductsUseCase, clearCartUseCase, scheduler)

    @Singleton
    @Provides
    fun providesCartPresenter(
        increaseCountUseCase: IncreaseCountUseCase,
        decreaseCountUseCase: DecreaseCountUseCase,
        removeFromCartUseCase: RemoveFromCartUseCase,
        getCartProductsUseCase: GetCartProductsUseCase,
        clearCartUseCase: ClearCartUseCase,
        scheduler: SchedulerProvider
    ) = CartPresenter(increaseCountUseCase, decreaseCountUseCase, removeFromCartUseCase, getCartProductsUseCase, clearCartUseCase, scheduler)

}