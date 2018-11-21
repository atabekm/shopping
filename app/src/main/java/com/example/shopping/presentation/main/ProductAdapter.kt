package com.example.shopping.presentation.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.shopping.R
import com.example.shopping.domain.model.Product
import com.example.shopping.presentation.toCurrency

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private lateinit var productList: List<Product>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    fun setData(data: List<Product>) {
        productList = data
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(product: Product) {
            view.findViewById<TextView>(R.id.product_title).text = product.title
            view.findViewById<TextView>(R.id.product_description).text = product.description
            view.findViewById<TextView>(R.id.product_price).text = product.price.toCurrency()
        }
    }
}