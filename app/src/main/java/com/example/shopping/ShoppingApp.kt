package com.example.shopping

import android.app.Application
import com.example.shopping.di.component.AppComponent
import com.example.shopping.di.component.DaggerAppComponent
import com.example.shopping.di.module.AppModule

class ShoppingApp : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}