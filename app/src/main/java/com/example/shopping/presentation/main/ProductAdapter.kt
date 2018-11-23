package com.example.shopping.presentation.main

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shopping.R
import com.example.shopping.domain.model.Product
import com.example.shopping.presentation.toCurrency
import com.example.shopping.presentation.visible
import kotlinx.android.synthetic.main.item_product.view.*



class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var productList: List<Product> = listOf()
    private var addToCartArray: MutableList<Int> = mutableListOf()
    lateinit var addToCartCallback: (Product) -> Unit

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

    fun resetCartButtonState() {
        addToCartArray.clear()
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(product: Product) {
            view.productTitle.text = product.title
            view.productDescription.text = product.description
            view.productPriceOld.paintFlags = view.productPriceOld.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            view.productPriceOld.text = product.getOldPrice().toCurrency()
            view.productPriceOld.visible(product.getOldPrice() > 0)
            view.productPriceNew.text = product.getNewPrice().toCurrency()
            view.productCart.isEnabled = !addToCartArray.contains(product.id)
            view.productCart.setOnClickListener {
                addToCartCallback.invoke(product)
                addToCartArray.add(product.id)
                view.productCart.isEnabled = false
            }
        }
    }
}