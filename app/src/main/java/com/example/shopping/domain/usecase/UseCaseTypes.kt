package com.example.shopping.domain.usecase

import io.reactivex.Observable
import io.reactivex.Single

interface UseCaseWithParameters<P, R> {
    fun execute(parameter: P) : R
}

interface SingleUseCase<T> {
    fun execute(): Single<T>
}

interface ObservableUseCase<R> {
    fun execute() : Observable<R>
}