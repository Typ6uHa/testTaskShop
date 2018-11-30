package com.example.tony.shopapp.data.api.repository

import com.example.tony.shopapp.data.api.ApiFactory
import com.example.tony.shopapp.data.api.service.ShopService
import com.example.tony.shopapp.data.model.Banner
import com.example.tony.shopapp.data.model.Offer
import io.reactivex.Single

class ShopRepositoryImpl : ShopRepository {

    private val service = ApiFactory.getRetrofitService(ShopService::class)

    override fun getBanners(): Single<List<Banner>> {
        return service.getBanners()
    }

    override fun getOffers(): Single<List<Offer>> {
        return service.getOffers()
    }

}