package com.example.shopping.presentation.base

interface BasePresenter<T> {
    fun attach(view: T)
    fun detach()
}