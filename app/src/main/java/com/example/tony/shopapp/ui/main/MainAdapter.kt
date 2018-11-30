package com.example.tony.shopapp.ui.main

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.tony.shopapp.R
import com.example.tony.shopapp.ui.base.swipeRevealLayout.SwipeRevealLayout
import com.example.tony.shopapp.ui.base.swipeRevealLayout.ViewBinderHelper
import com.example.tony.shopapp.ui.main.model.AdapterModel
import com.example.tony.shopapp.ui.main.model.Divider
import com.example.tony.shopapp.ui.main.model.OfferItem
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.lang.IllegalArgumentException

const val DIVIDER = 0
const val OFFER = 1

class MainAdapter : ListAdapter<AdapterModel, MainAdapter.ViewHolder>(DIFF_CALLBACK) {

    val viewBinderHelper = ViewBinderHelper()

    init {
        viewBinderHelper.setOpenOnlyOne(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType){
            DIVIDER -> DividerViewHolder(inflater.inflate(R.layout.item_divider,parent,false))
            OFFER -> OfferViewHolder(inflater.inflate(R.layout.item_product,parent,false))
            else -> throw IllegalArgumentException("Wrong viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder.itemViewType == OFFER){
            viewBinderHelper.bind((holder as OfferViewHolder).swpl, getItem(position).toString())
        }
        holder.bind(getItem(position))
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: AdapterModel)
    }

    override fun getItemViewType(position: Int): Int {
        val item: AdapterModel = getItem(position)

        return when (item) {
            is OfferItem -> OFFER
            is Divider -> DIVIDER
        }
    }

     class OfferViewHolder(itemView: View) : ViewHolder(itemView){
        val swpl : SwipeRevealLayout = itemView.findViewById(R.id.swpl)
        val title: TextView = itemView.findViewById(R.id.tvProductName)
        val desc: TextView = itemView.findViewById(R.id.tvProductDescription)
        val image: ImageView = itemView.findViewById(R.id.ivProductImage)
        val price: TextView = itemView.findViewById(R.id.tvCurrentPrice)
        val discount: TextView = itemView.findViewById(R.id.tvDiscount)
        val priceWithoutDiscount: TextView = itemView.findViewById(R.id.tvPriceWithoutDiscount)

        private val transform = RoundedCornersTransformation(5,5)
        override fun bind(item: AdapterModel) {
            item as OfferItem
            title.text = item.title
            desc.text = item.desc
            Picasso.get()
                    .load(item.image)
                    .transform(transform)
                    .into(image)

            if(item.discount != 0f && item.discount != null){
                discount.visibility = VISIBLE
                priceWithoutDiscount.visibility = VISIBLE
                // можно сделать через строкувый ресурс без конкатинации
                discount.text = (item.discount * 100).toString() + "%"
                if (item.price != null) {
                    price.text = (item.price / item.discount).toString() + "\u20BD"
                } else {
                    price.text = "sosi pisun"
                }
                priceWithoutDiscount.text = item.price.toString() + "\u20BD"
            } else {
                discount.visibility = GONE
                priceWithoutDiscount.visibility = GONE
                price.text = item.price.toString() + "\u20BD"
            }
        }
    }

    class DividerViewHolder(itemView: View) : ViewHolder(itemView){
        val dividerLabel : TextView = itemView.findViewById(R.id.tvDividerLabel)
        override fun bind(item: AdapterModel) {
            item as Divider
            dividerLabel.text = item.name
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AdapterModel>() {
            override fun areItemsTheSame(oldItem: AdapterModel, newItem: AdapterModel): Boolean {
                return oldItem::class == newItem::class
            }

            override fun areContentsTheSame(oldItem: AdapterModel, newItem: AdapterModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}