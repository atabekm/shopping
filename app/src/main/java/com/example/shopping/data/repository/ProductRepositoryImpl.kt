package com.example.shopping.data.repository

import com.example.shopping.domain.model.Product
import com.example.shopping.domain.repository.ProductRepository
import io.reactivex.Single

class ProductRepositoryImpl : ProductRepository {
    override fun getProductsList(): Single<List<Product>> {
        return Single.just(products)
    }

    private val products = listOf(
        Product(1, "Nintendo Switch", "Play your favourite games anytime, anywhere, with anyone, with Nintendo Switch", 400f),
        Product(2, "iPhone Xs Max", "iPhone XS is everything you love about iPhone. Taken to the extreme.", 2000f),
        Product(3, "Pixel 3 XL", "Pixel 3 and Pixel 3 XL are everything you wish your phone could do. Brilliant camera, all day battery, and Google Assistant with a squeeze", 1500f),
        Product(4, "Sony Alpha 7 III R", "Sony's latest A7 series cameras combine high resolution with high speed and professional performance", 5000f),
        Product(5, "Leica M10", "When you take a photograph with a Leica M-System camera, you experience a different kind of photography.", 10300f),
        Product(6, "Apple AirPods", "With 24-hour battery life and groundbreaking ease of use and intelligence, AirPods are wireless headphones unlike any other.", 200f),
        Product(7, "Sennheiser PXC550", "Superior quality headphones with top level of comfort, deep sound and compactness", 430f),
        Product(8, "B&W PX", "Great noise cancelling, bluetooth capabilities and sound quality", 550f),
        Product(9, "AudioQuest Nighthawk", "Sophisticated bio-friendly Headphone with style, luxurious comfort and high quality sound.", 1000f),
        Product(10, "HHKB Pro 2", "A movement, redefining what a professional keyboard", 500f),
        Product(11, "Ergodox EZ", "An open-source keyboard for serious developers, gamers, and people who care deeply about their craft.", 1350f),
        Product(12, "X-Bows Ergonomic", "Ergo mech keyboard that great for long days at work and intense gaming sessions", 170f)
    )

}