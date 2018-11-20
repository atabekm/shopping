package com.example.shopping.domain.repository

import com.example.shopping.domain.model.Product
import io.reactivex.Single

interface ProductRepository {
    fun getProductsList() : Single<List<Product>>
}
