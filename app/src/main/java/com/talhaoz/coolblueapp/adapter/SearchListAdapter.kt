package com.talhaoz.coolblueapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.talhaoz.coolblueapp.R
import com.talhaoz.coolblueapp.data.model.Product
import com.talhaoz.coolblueapp.databinding.ItemProductCardBinding
import com.talhaoz.coolblueapp.util.gone
import com.talhaoz.coolblueapp.util.loadImage
import com.talhaoz.coolblueapp.util.visible

class SearchListAdapter(
    private val onAddToShoppingCartClick: (Product) -> Unit
) : RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>() {

    private lateinit var productList: ArrayList<Product>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        val binding =
            ItemProductCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return SearchListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        val product = productList[position]
        with(holder) {

            hideConditionalViews(this)

            tvProductName.text = product.productName

            product.productImage?.let { imageUrl ->
                ivProductImage.loadImage(imageUrl)
            }

            product.promoIcon?.let { promoIcon ->
                if (PromoType.COOL_BLUE_CHOICE.get == promoIcon.type) {
                    ivCoolBluesChoice.visible()
                    tvCoolBlueChoiceText.visible()
                    tvCoolBlueChoiceText.text = promoIcon.text
                } else {
                    tvPromoText.visible()
                    tvPromoText.text = promoIcon.text
                }
            }

            product.listPriceIncVat?.let { listPriceIncVat ->
                containerOldPrice.visible()
                tvOldPrice.text = listPriceIncVat.toString()
            }

            tvPrice.text = product.salesPriceIncVat.toString()

            product.nextDayDelivery?.let { nextDayDelivery ->
                if (nextDayDelivery)
                    containerTomorrowDelivery.visible()
            }

            product.reviewInformation?.reviewSummary?.let { reviewSummary ->
                reviewSummary.reviewAverage?.let { ratingBar.rating = (it / 2).toFloat() }
                reviewSummary.reviewCount?.let { tvRatingCount.text = "($it)" }
            }

            setFavorite(product.isFavorite, ivFavorite)
            ivFavorite.setOnClickListener {
                product.isFavorite = !product.isFavorite
                setFavorite(product.isFavorite, ivFavorite)
            }

            ivAddToShoppingCart.setOnClickListener {
                onAddToShoppingCartClick.invoke(product)
            }

            product.USPs?.let { usps ->
                tvProductFeatures.visible()
                var productFeatures = ""
                for (i in usps.indices) {
                    productFeatures += usps[i]
                    if (i != usps.size - 1)
                        productFeatures += "  |  "
                }
                tvProductFeatures.text = productFeatures
            }
        }
    }

    private fun setFavorite(isFavorite: Boolean, imageView: ImageView) {
        if (isFavorite)
            imageView.setImageResource(R.drawable.ic_favorite_filled)
        else
            imageView.setImageResource(R.drawable.ic_favorite_empty)
    }

    private fun hideConditionalViews(viewHolder: SearchListViewHolder) {
        with(viewHolder) {
            ivCoolBluesChoice.gone()
            tvCoolBlueChoiceText.gone()
            tvPromoText.gone()
            containerOldPrice.gone()
            containerTomorrowDelivery.gone()
            tvProductFeatures.gone()
        }
    }

    fun submitList(products: ArrayList<Product>) {
        productList = products
        notifyDataSetChanged()
    }

    fun clearList() {
        productList.clear()
        notifyDataSetChanged()
    }

    inner class SearchListViewHolder(binding: ItemProductCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val ivCoolBluesChoice: ImageView = binding.ivCoolBluesChoiceBadge
        val ivProductImage: ImageView = binding.ivProductImage
        val ivFavorite: ImageView = binding.ivFavorite
        val tvPromoText: TextView = binding.tvPromoText
        val tvProductName: TextView = binding.tvProductName
        val containerOldPrice: ConstraintLayout = binding.containerOldPrice
        val tvOldPrice: TextView = binding.tvOldPrice
        val tvPrice: TextView = binding.tvPrice
        val ratingBar: RatingBar = binding.ratingBar
        val tvRatingCount: TextView = binding.tvRatingCount
        val tvProductFeatures: TextView = binding.tvProductFeatures
        val tvCoolBlueChoiceText: TextView = binding.tvCoolBlueChoiceText
        val containerTomorrowDelivery: Group = binding.containerTomorrowDelivery
        val ivAddToShoppingCart: ImageView = binding.ivAddToShoppingCart
    }
}

enum class PromoType(val get: String) {
    COOL_BLUE_CHOICE("coolblues-choice")
}