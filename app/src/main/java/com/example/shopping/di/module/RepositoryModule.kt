package com.example.shopping.di.module

import com.example.shopping.data.repository.CartRepositoryImpl
import com.example.shopping.data.repository.ProductRepositoryImpl
import com.example.shopping.domain.repository.CartRepository
import com.example.shopping.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesProductRepository(): ProductRepository = ProductRepositoryImpl()

    @Provides
    @Singleton
    fun providesCartRepository(): CartRepository = CartRepositoryImpl()

}