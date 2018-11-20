package com.example.shopping.di.module

import com.example.shopping.domain.repository.ProductRepository
import com.example.shopping.domain.usecase.product.GetProductsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun providesGetProductsUseCase(repository: ProductRepository) = GetProductsUseCase(repository)

}