package com.example.shopping.presentation

import java.text.NumberFormat

fun Float.toCurrency(): String {
    return NumberFormat.getCurrencyInstance().format(this)
}