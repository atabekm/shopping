package com.example.shopping.domain.repository

import com.example.shopping.domain.model.Cart
import com.example.shopping.domain.model.Product
import io.reactivex.Observable

interface CartRepository {
    fun addToCart(product: Product)
    fun removeFromCart(product: Product)
    fun getProductsInCart(): Observable<List<Cart>>
}