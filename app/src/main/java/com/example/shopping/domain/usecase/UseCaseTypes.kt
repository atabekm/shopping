package com.example.shopping.domain.usecase

import io.reactivex.Single

interface SingleUseCase<T> {
    fun execute(): Single<T>
}