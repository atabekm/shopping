package com.example.shopping.data.repository

import com.example.shopping.domain.model.Cart
import com.example.shopping.domain.model.Product
import com.example.shopping.domain.repository.CartRepository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class CartRepositoryImpl : CartRepository {
    private val carts: HashMap<Int, Cart> = HashMap()
    private val subject: BehaviorSubject<List<Cart>> = BehaviorSubject.createDefault(listOf())

    override fun increaseProductCount(product: Product) {
        val cart = carts[product.id]
        if (cart == null) {
            carts[product.id] = Cart(1, product)
        } else{
            cart.count += 1
        }
        subject.onNext(carts.values.toList())
    }

    override fun decreaseProductCount(product: Product) {
        val cart = carts[product.id]
        if (cart != null) {
            if (cart.count > 1) {
                cart.count -= 1
            } else {
                carts.remove(product.id)
            }
        }
        subject.onNext(carts.values.toList())
    }

    override fun removeFromCart(product: Product) {
        carts.remove(product.id)
        subject.onNext(carts.values.toList())
    }

    override fun getProductsInCart(): Observable<List<Cart>> = subject

    override fun clearCart() {
        carts.clear()
        subject.onNext(carts.values.toList())
    }
}