package com.example.shopping.di.module

import android.content.Context
import com.example.shopping.ShoppingApp
import com.example.shopping.di.AppSchedulers
import com.example.shopping.di.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: ShoppingApp) {

    @Provides
    @Singleton
    fun providesContext(): Context = app.applicationContext

    @Provides
    @Singleton
    fun providesScheduler(): SchedulerProvider = AppSchedulers()

}