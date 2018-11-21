package com.example.shopping.presentation.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.shopping.R
import com.example.shopping.ShoppingApp
import com.example.shopping.domain.model.Product
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {
    private val adapter = ProductAdapter()

    @Inject lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as ShoppingApp).component.inject(this)

        productRecycler.layoutManager = LinearLayoutManager(this)
        productRecycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        productRecycler.adapter = adapter
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
        adapter.setData(products)
    }

    override fun errorRetrieving(message: String?) {
        Toast.makeText(this, "Error retrieving products: $message", Toast.LENGTH_LONG).show()
    }
}
