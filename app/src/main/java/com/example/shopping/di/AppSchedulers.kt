package com.example.shopping.di

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppSchedulers : SchedulerProvider {
    override fun ui() = AndroidSchedulers.mainThread()
    override fun io() = Schedulers.io()
}