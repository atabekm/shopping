package com.example.shopping.presentation.main

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.shopping.R
import com.example.shopping.domain.model.Product
import com.example.shopping.presentation.toCurrency
import com.example.shopping.presentation.visible
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_sorter.view.*

class ProductAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var productList: List<Product> = listOf()
    private var cartIds: List<Int> = listOf()
    lateinit var addToCartCallback: (Product) -> Unit
    lateinit var sortingIndexCallback: (Int) -> Unit

    companion object {
        private const val TYPE_SORTER = 0
        private const val TYPE_PRODUCT = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SORTER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sorter, parent, false)
                val holder = SorterViewHolder(view)
                holder.bind()
                holder
            }
            TYPE_PRODUCT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
                ProductViewHolder(view)
            }
            else -> EmptyViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_PRODUCT) {
            (holder as ProductViewHolder).bind(productList[position - 1])
        }
    }

    override fun getItemCount(): Int = productList.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_SORTER
        } else {
            TYPE_PRODUCT
        }
    }

    fun setData(data: List<Product>, cartIds: List<Int>, sortingIndex: Int) {
        productList = data
        this.cartIds = cartIds
        changeSorting(sortingIndex)
    }

    fun changeSorting(index: Int) {
        when(index) {
            0 -> productList = productList.sortedBy { it.id }
            1 -> productList = productList.sortedBy { it.getNewPrice() }
            2 -> productList = productList.sortedByDescending { it.getNewPrice() }
            3 -> productList = productList.sortedBy { it.title.toLowerCase() }
            4 -> productList = productList.sortedByDescending { it.title.toLowerCase() }
        }
        notifyItemRangeChanged(1, productList.size + 1)
    }

    inner class ProductViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(product: Product) {
            view.productTitle.text = product.title
            view.productDescription.text = product.description
            view.productPriceOld.paintFlags = view.productPriceOld.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            view.productPriceOld.text = product.getOldPrice().toCurrency()
            view.productPriceOld.visible(product.getOldPrice() > 0)
            view.productPriceNew.text = product.getNewPrice().toCurrency()
            view.productCart.isEnabled = !cartIds.contains(product.id)
            view.productCart.setOnClickListener {
                addToCartCallback.invoke(product)
                view.productCart.isEnabled = false
            }
        }
    }

    inner class SorterViewHolder(val view: View): RecyclerView.ViewHolder(view), AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            sortingIndexCallback.invoke(position)
            changeSorting(position)
        }

        fun bind() {
            view.spinnerSort.onItemSelectedListener = this
            ArrayAdapter.createFromResource(
                view.context,
                R.array.sorter_types,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                view.spinnerSort.adapter = adapter
            }
        }
    }

    inner class EmptyViewHolder(val view: View): RecyclerView.ViewHolder(view)
}