package com.example.shopping.presentation.cart

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.shopping.R
import com.example.shopping.domain.model.Cart
import com.example.shopping.presentation.toCurrency

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var cartList: List<Cart> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartList[position])
    }

    override fun getItemCount(): Int = cartList.size

    fun setData(data: List<Cart>) {
        cartList = data
        notifyDataSetChanged()
    }

    inner class CartViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(cart: Cart) {
            view.findViewById<TextView>(R.id.cart_item_title).text = cart.product.title
            view.findViewById<TextView>(R.id.cart_item_description).text = cart.product.description
            view.findViewById<TextView>(R.id.cart_item_price).text = cart.product.price.toCurrency()
        }
    }
}