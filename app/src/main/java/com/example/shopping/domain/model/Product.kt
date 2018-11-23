package com.example.shopping.domain.model

data class Product(val id: Int, val title: String, val description: String, val price: Float, val salePrice: Float = 0f) {
    fun getOldPrice(): Float {
        return if (salePrice > 0) {
            price
        } else {
            0f
        }
    }

    fun getNewPrice(): Float {
        return if (salePrice > 0) {
            salePrice
        } else {
            price
        }
    }
}