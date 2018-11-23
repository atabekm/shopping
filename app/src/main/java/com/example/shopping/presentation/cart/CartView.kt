package com.example.shopping.presentation.cart

import com.example.shopping.domain.model.Cart

interface CartView {
    fun updateCart(carts: List<Cart>)
    fun updateTotalPrice(price: String)
    fun error(message: String?)
}