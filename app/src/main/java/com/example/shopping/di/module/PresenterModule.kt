package com.example.shopping.di.module

import com.example.shopping.domain.usecase.product.GetProductsUseCase
import com.example.shopping.presentation.main.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Singleton
    @Provides
    fun providesMainPresenter(getProductsUseCase: GetProductsUseCase) = MainPresenter(getProductsUseCase)

}