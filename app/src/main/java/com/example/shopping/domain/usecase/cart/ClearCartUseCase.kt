package com.example.shopping.domain.usecase.cart

import com.example.shopping.domain.repository.CartRepository
import com.example.shopping.domain.usecase.UseCase

class ClearCartUseCase(val repository: CartRepository) : UseCase {

    override fun execute() {
        repository.clearCart()
    }

}