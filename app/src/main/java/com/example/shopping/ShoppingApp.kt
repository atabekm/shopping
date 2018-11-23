package com.example.shopping

import android.app.Application
import com.example.shopping.di.component.AppComponent
import com.example.shopping.di.component.DaggerAppComponent

class ShoppingApp : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}