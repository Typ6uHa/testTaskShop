package com.example.tony.shopapp.data.model

data class Offer(
        val id: String,
        val name: String,
        val desc: String?,
        val groupName: String,
        val type: String,
        val image: String,
        val price: Float?,
        val discount: Float?
)