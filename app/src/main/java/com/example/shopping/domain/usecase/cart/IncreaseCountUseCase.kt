package com.example.shopping.domain.usecase.cart

import com.example.shopping.domain.model.Product
import com.example.shopping.domain.repository.CartRepository
import com.example.shopping.domain.usecase.UseCaseWithParameters

class IncreaseCountUseCase(val repository: CartRepository) : UseCaseWithParameters<Product, Unit> {

    override fun execute(parameter: Product) {
        repository.increaseProductCount(parameter)
    }

}