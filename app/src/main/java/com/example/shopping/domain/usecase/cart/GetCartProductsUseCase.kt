package com.example.shopping.domain.usecase.cart

import com.example.shopping.domain.model.Cart
import com.example.shopping.domain.repository.CartRepository
import com.example.shopping.domain.usecase.ObservableUseCase
import io.reactivex.Observable

class GetCartProductsUseCase(val repository: CartRepository): ObservableUseCase<List<Cart>> {

    override fun execute(): Observable<List<Cart>> {
        return repository.getProductsInCart()
    }

}