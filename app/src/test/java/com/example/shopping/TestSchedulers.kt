package com.example.shopping

import com.example.shopping.di.SchedulerProvider
import io.reactivex.schedulers.Schedulers

class TestSchedulers : SchedulerProvider {
    override fun ui() = Schedulers.trampoline()
    override fun io() = Schedulers.trampoline()
}