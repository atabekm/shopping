package com.example.shopping.presentation.main

import com.example.shopping.domain.model.Product

interface MainView {
    fun updateProducts(products: List<Product>, cartIds: List<Int>)
    fun updateCartCount(count: Int)
    fun errorRetrieving(message: String?)
}