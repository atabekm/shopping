package com.example.shopping.presentation.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.shopping.R
import com.example.shopping.ShoppingApp
import com.example.shopping.domain.model.Product
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    @Inject lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as ShoppingApp).component.inject(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
        presenter.fetchProducts()
    }

    override fun onStop() {
        presenter.detach()
        super.onStop()
    }

    override fun updateProducts(products: List<Product>) {
        Log.d("mytest", "product size: " + products.size)
    }

    override fun errorRetrieving(message: String?) {
        Log.e("mytest", "Error retrieving products: $message")
    }
}
