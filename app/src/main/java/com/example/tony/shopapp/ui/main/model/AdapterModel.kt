package com.example.tony.shopapp.ui.main.model

sealed class AdapterModel

class OfferItem(val title: String, val desc: String?, val image: String,
                val price: Float?, val discount: Float?) : AdapterModel()

class Divider(val name: String) : AdapterModel()