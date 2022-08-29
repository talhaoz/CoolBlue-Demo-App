package com.talhaoz.coolblueapp.repository

import android.accounts.NetworkErrorException
import com.talhaoz.coolblueapp.data.model.SearchModel
import com.talhaoz.coolblueapp.domain.repository.SearchRepository

class FakeSearchRepository : SearchRepository {

    private val searchModel : SearchModel = SearchModel(
        currentPage = 1,
        pageCount = 3,
        pageSize = 24,
        products = listOf(),
        totalResults = 24
    )

    private var shouldReturnNetworkError = true

    fun setShouldReturnNetworkError(value : Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun getSearchResults(query: String, page: Int): SearchModel {
        return if(shouldReturnNetworkError) {
            throw NetworkErrorException("Network Error")
        } else {
            searchModel
        }
    }
}