package com.example.shopping.domain.usecase.product

import com.example.shopping.domain.model.Product
import com.example.shopping.domain.repository.ProductRepository
import com.example.shopping.domain.usecase.SingleUseCase
import io.reactivex.Single

class GetProductsUseCase(private val repository: ProductRepository): SingleUseCase<List<Product>> {

    override fun execute(): Single<List<Product>> = repository.getProductsList()

}