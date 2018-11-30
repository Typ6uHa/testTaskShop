package com.example.tony.shopapp.data.api.repository

object ShopRepositoryProvider {
    val instance: ShopRepository by lazy {
        ShopRepositoryImpl()
    }
}