package com.example.tony.shopapp.data.api.service

import com.example.tony.shopapp.data.model.Banner
import com.example.tony.shopapp.data.model.Offer
import io.reactivex.Single
import retrofit2.http.GET

interface ShopService {
    @GET ("banners.json")
    fun getBanners():Single<List<Banner>>

    @GET("offers.json")
    fun getOffers():Single<List<Offer>>
}