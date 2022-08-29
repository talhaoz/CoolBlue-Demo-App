package com.talhaoz.coolblueapp.data.model

data class Product(
    val USPs: List<String>?,
    val availabilityState: Int?,
    val coolbluesChoiceInformationTitle: String?,
    val listPriceExVat: Double?,
    val listPriceIncVat: Double?,
    val nextDayDelivery: Boolean?,
    val productId: Int?,
    val productImage: String?,
    val productName: String?,
    val promoIcon: PromoIcon?,
    val reviewInformation: ReviewInformation?,
    val salesPriceIncVat: Double?,
    var isFavorite: Boolean = false
)