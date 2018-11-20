package com.example.shopping.di.module

import android.content.Context
import com.example.shopping.ShoppingApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: ShoppingApp) {

    @Provides
    @Singleton
    fun providesContext(): Context = app.applicationContext

}