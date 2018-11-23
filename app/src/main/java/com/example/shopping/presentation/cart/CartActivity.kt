package com.example.shopping.presentation.cart

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.Toast
import com.example.shopping.R
import com.example.shopping.ShoppingApp
import com.example.shopping.domain.model.Cart
import kotlinx.android.synthetic.main.activity_cart.*
import javax.inject.Inject

class CartActivity : AppCompatActivity(), CartView {
    private val adapter = CartAdapter()

    @Inject lateinit var presenter: CartPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        (application as ShoppingApp).component.inject(this)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        cartRecycler.layoutManager = LinearLayoutManager(this)
        cartRecycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        cartRecycler.itemAnimator = DefaultItemAnimator()
        cartRecycler.adapter = adapter

        adapter.increaseCountCallback = { presenter.increaseProductCount(it) }
        adapter.decreaseCountCallback = { presenter.decreaseProductCount(it) }
        adapter.cartMenuCallback = { presenter.removeFromCart(it) }
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
    }

    override fun onStop() {
        presenter.detach()
        super.onStop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun updateCart(carts: List<Cart>) {
        adapter.setData(carts)
    }

    override fun updateTotalPrice(price: String) {
        cartTotalPriceAmount.text = price
    }

    override fun updateButtonEnabled(enabled: Boolean) {
        cartBuyButton.isEnabled = enabled
    }

    override fun error(message: String?) {
        Toast.makeText(this, "Error retrieving cart: $message", Toast.LENGTH_LONG).show()
    }
}