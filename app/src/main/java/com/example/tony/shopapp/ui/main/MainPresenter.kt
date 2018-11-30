package com.example.tony.shopapp.ui.main

import com.arellomobile.mvp.InjectViewState
import com.example.tony.shopapp.addSchedulers
import com.example.tony.shopapp.data.api.repository.ShopRepositoryProvider
import com.example.tony.shopapp.data.model.Offer
import com.example.tony.shopapp.ui.base.BasePresenter
import com.example.tony.shopapp.ui.main.model.AdapterModel
import com.example.tony.shopapp.ui.main.model.Divider
import com.example.tony.shopapp.ui.main.model.OfferItem

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {

    private val shopRepository = ShopRepositoryProvider.instance
    private var listOffer: List<Offer> = emptyList()
    private var postViewModels: MutableList<AdapterModel> = mutableListOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        createPostViewModels()
    }

    private fun createPostViewModels() {
        shopRepository.getOffers()
                .map {
                    val postViewModels: MutableList<AdapterModel> = mutableListOf()
                    val listOffer: List<Offer> = it
                    listOffer.groupBy { it -> it.type }
                            .iterator()
                            .forEach {
                                postViewModels.add(Divider(it.key))
                                postViewModels.addAll(postViewModelsConverter(it.value))
                            }
                    Pair(listOffer, postViewModels)
                }
                .addSchedulers()
                .subscribe { pair ->
                    this.postViewModels = pair.second
                    listOffer = pair.first
                    viewState.setList(this.postViewModels)
                }
        viewState.setList(postViewModels)
    }

    private fun postViewModelsConverter(list: List<Offer>): List<OfferItem> {
        val listOfferItem: MutableList<OfferItem> = mutableListOf()
        list.forEach {
            listOfferItem.add(OfferItem(
                    title = it.name,
                    desc = it.desc,
                    discount = it.discount,
                    price = it.price,
                    image = it.image
            ))
        }
        return listOfferItem
    }
}