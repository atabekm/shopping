package com.example.shopping.presentation.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.shopping.R
import com.example.shopping.ShoppingApp
import com.example.shopping.domain.model.Product
import com.example.shopping.presentation.cart.CartActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var badgeCounter: TextView
    private val adapter = ProductAdapter()

    @Inject lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as ShoppingApp).component.inject(this)

        productRecycler.layoutManager = LinearLayoutManager(this)
        productRecycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        productRecycler.adapter = adapter
        adapter.addToCartCallback = { product: Product -> presenter.addToCart(product) }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val cartMenu = menu.findItem(R.id.menu_cart)
        val root = cartMenu.actionView
        root.setOnClickListener { startActivity(Intent(this, CartActivity::class.java)) }
        badgeCounter = root.findViewById(R.id.cart_badge_counter)

        return true
    }

    override fun updateProducts(products: List<Product>) {
        adapter.setData(products)
    }

    override fun errorRetrieving(message: String?) {
        Toast.makeText(this, "Error retrieving products: $message", Toast.LENGTH_LONG).show()
    }

    override fun updateCartCount(count: Int) {
        if (count == 0) {
            badgeCounter.visibility = View.GONE
        } else {
            badgeCounter.visibility = View.VISIBLE
            badgeCounter.text = "$count"
        }
    }
}
