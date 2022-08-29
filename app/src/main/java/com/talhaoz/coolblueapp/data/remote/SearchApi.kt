package com.talhaoz.coolblueapp.data.remote

import com.talhaoz.coolblueapp.data.model.SearchModel
import com.talhaoz.coolblueapp.util.Constants.SEARCH_END_POINT
import com.talhaoz.coolblueapp.util.Constants.SEARCH_PARAM_PAGE
import com.talhaoz.coolblueapp.util.Constants.SEARCH_PARAM_QUERY
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET(SEARCH_END_POINT)
    suspend fun getSearchResults(
        @Query(SEARCH_PARAM_QUERY) query: String,
        @Query(SEARCH_PARAM_PAGE) page: Int
    ): SearchModel
}