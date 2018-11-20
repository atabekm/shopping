package com.example.shopping.presentation.main

import com.example.shopping.domain.model.Product

interface MainView {
    fun updateProducts(products: List<Product>)
    fun errorRetrieving(message: String?)
}