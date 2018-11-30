package com.example.tony.shopapp.data.api.repository

import com.example.tony.shopapp.data.model.Banner
import com.example.tony.shopapp.data.model.Offer
import io.reactivex.Single

interface ShopRepository {
    fun getBanners():Single<List<Banner>>
    fun getOffers():Single<List<Offer>>
}