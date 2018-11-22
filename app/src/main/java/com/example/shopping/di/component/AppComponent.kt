package com.example.shopping.di.component

import com.example.shopping.ShoppingApp
import com.example.shopping.di.module.AppModule
import com.example.shopping.di.module.PresenterModule
import com.example.shopping.di.module.RepositoryModule
import com.example.shopping.di.module.UseCaseModule
import com.example.shopping.presentation.cart.CartActivity
import com.example.shopping.presentation.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, RepositoryModule::class, UseCaseModule::class, PresenterModule::class))
interface AppComponent {
    fun inject(app: ShoppingApp)
    fun inject(activity: MainActivity)
    fun inject(activity: CartActivity)
}
