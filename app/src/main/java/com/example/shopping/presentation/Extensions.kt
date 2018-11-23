package com.example.shopping.presentation

import android.view.View
import java.text.NumberFormat

fun Float.toCurrency(): String {
    return NumberFormat.getCurrencyInstance().format(this)
}

fun View.visible(isVisible: Boolean) {
    this.visibility = when(isVisible) {
        true -> View.VISIBLE
        false -> View.GONE
    }
}