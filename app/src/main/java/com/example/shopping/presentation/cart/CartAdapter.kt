package com.example.shopping.presentation.cart

import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.shopping.R
import com.example.shopping.domain.model.Cart
import com.example.shopping.presentation.toCurrency
import kotlinx.android.synthetic.main.item_cart.view.*

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var cartList: List<Cart> = listOf()
    lateinit var increaseCountCallback: (Cart) -> Unit
    lateinit var decreaseCountCallback: (Cart) -> Unit
    lateinit var cartMenuCallback: (Cart) -> Unit

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

    inner class CartViewHolder(val view: View) : RecyclerView.ViewHolder(view), PopupMenu.OnMenuItemClickListener {
        private lateinit var currentCart: Cart

        override fun onMenuItemClick(item: MenuItem): Boolean {
            return when(item.itemId) {
                R.id.menu_remove_from_cart -> {
                    cartMenuCallback.invoke(currentCart)
                    true
                }
                else -> false
            }
        }

        fun bind(cart: Cart) {
            currentCart = cart

            view.cartItemTitle.text = cart.product.title
            view.cartItemDescription.text = cart.product.description
            view.cartItemPrice.text = cart.product.price.toCurrency()
            view.cartItemQuantityCount.text = cart.count.toString()
            view.cartItemDecreaseCount.isEnabled = cart.count > 1
            view.cartItemDecreaseCount.setOnClickListener{
                decreaseCountCallback.invoke(cart)
            }
            view.cartItemIncreaseCount.isEnabled = cart.count < 10
            view.cartItemIncreaseCount.setOnClickListener {
                increaseCountCallback.invoke(cart)
            }
            view.cartMenu.setOnClickListener {
                PopupMenu(view.context, view.cartMenu).apply {
                    setOnMenuItemClickListener(this@CartViewHolder)
                    inflate(R.menu.cart_menu)
                    show()
                }
            }
        }
    }
}