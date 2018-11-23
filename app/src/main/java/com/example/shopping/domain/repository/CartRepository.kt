package com.example.shopping.domain.repository

import com.example.shopping.domain.model.Cart
import com.example.shopping.domain.model.Product
import io.reactivex.Observable

interface CartRepository {
    fun increaseProductCount(product: Product)
    fun decreaseProductCount(product: Product)
    fun removeFromCart(product: Product)
    fun getProductsInCart(): Observable<List<Cart>>
    fun clearCart()
}