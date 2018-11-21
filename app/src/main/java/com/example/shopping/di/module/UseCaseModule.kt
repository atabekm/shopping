package com.example.shopping.di.module

import com.example.shopping.domain.repository.CartRepository
import com.example.shopping.domain.repository.ProductRepository
import com.example.shopping.domain.usecase.cart.AddToCartUseCase
import com.example.shopping.domain.usecase.cart.GetCartProductsUseCase
import com.example.shopping.domain.usecase.cart.RemoveFromCartUseCase
import com.example.shopping.domain.usecase.product.GetProductsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun providesGetProductsUseCase(repository: ProductRepository) = GetProductsUseCase(repository)

    @Singleton
    @Provides
    fun providesAddToCartUseCase(repository: CartRepository) = AddToCartUseCase(repository)

    @Singleton
    @Provides
    fun providesRemoveFromCartUseCase(repository: CartRepository) = RemoveFromCartUseCase(repository)

    @Singleton
    @Provides
    fun providesGetCartProductsUseCase(repository: CartRepository) = GetCartProductsUseCase(repository)

}