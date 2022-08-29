package com.talhaoz.coolblueapp.data.repository

import com.talhaoz.coolblueapp.data.model.SearchModel
import com.talhaoz.coolblueapp.data.remote.SearchApi
import com.talhaoz.coolblueapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private var searchApi: SearchApi
) : SearchRepository {

    override suspend fun getSearchResults(query: String, page: Int): SearchModel =
        searchApi.getSearchResults(query, page)
}