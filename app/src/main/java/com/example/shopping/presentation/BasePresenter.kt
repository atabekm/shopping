package com.example.shopping.presentation

interface BasePresenter<T> {
    fun attach(view: T)
    fun detach()
}