package com.talhaoz.coolblueapp.data.model

data class SearchModel(
    val currentPage: Int?,
    val pageCount: Int?,
    val pageSize: Int?,
    val products: List<Product>?,
    val totalResults: Int?
)