package com.talhaoz.coolblueapp.domain.repository

import com.talhaoz.coolblueapp.data.model.SearchModel

interface SearchRepository {
    suspend fun getSearchResults(query: String, page: Int): SearchModel
}