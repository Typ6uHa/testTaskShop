package com.example.tony.shopapp.ui.main

import com.example.tony.shopapp.ui.base.BaseView
import com.example.tony.shopapp.ui.main.model.AdapterModel

interface MainView : BaseView {

    fun setList(list: List<AdapterModel>)

}
