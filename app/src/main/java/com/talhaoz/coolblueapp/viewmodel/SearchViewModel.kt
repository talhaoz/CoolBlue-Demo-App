package com.talhaoz.coolblueapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talhaoz.coolblueapp.data.Resource
import com.talhaoz.coolblueapp.data.model.Product
import com.talhaoz.coolblueapp.domain.usecase.SearchUseCase
import com.talhaoz.coolblueapp.util.Constants.QUERY_LENGTH_LIMIT
import com.talhaoz.coolblueapp.util.Constants.SEARCH_QUERY_EMPTY_ERROR
import com.talhaoz.coolblueapp.util.Constants.SEARCH_QUERY_TOO_LONG_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _searchLiveData = MutableLiveData<Resource<List<Product>?>>()
    val searchLiveData: LiveData<Resource<List<Product>?>> get() = _searchLiveData

    private var totalPageCount = 0
    fun isLastPage() = totalPageCount == currentPageNum

    private var searchQuery = ""
    private var currentPageNum = 0
    private var searchResultResponse: ArrayList<Product>? = null

    fun getSearchResults(query: String = searchQuery) {
        viewModelScope.launch {
            if (query != searchQuery) {
                searchResultResponse = null
                currentPageNum = 0
            }
            if (query != "" && query.length < QUERY_LENGTH_LIMIT) {
                searchQuery = query
                searchUseCase.invoke(query, currentPageNum + 1).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _searchLiveData.value = Resource.loading()
                        }
                        is Resource.Success -> {
                            currentPageNum = result.data.currentPage ?: 1
                            if (searchResultResponse == null) {
                                totalPageCount = result.data.pageCount ?: 0
                                searchResultResponse = result.data.products as ArrayList<Product>
                            } else {
                                searchResultResponse?.addAll(result.data.products as ArrayList<Product>)
                            }
                            _searchLiveData.value = Resource.success(searchResultResponse)
                        }
                        is Resource.Failed -> {
                            _searchLiveData.value = Resource.failed(result.message, FailedType.ELSE)
                        }
                    }
                }
            } else {
                if (query == "")
                    _searchLiveData.value =
                        Resource.failed(SEARCH_QUERY_EMPTY_ERROR, FailedType.EMPTY_QUERY)
                else if (query.length > QUERY_LENGTH_LIMIT)
                    _searchLiveData.value =
                        Resource.failed(SEARCH_QUERY_TOO_LONG_ERROR, FailedType.LONG_QUERY)
            }
        }
    }
}

enum class FailedType {
    EMPTY_QUERY,
    LONG_QUERY,
    ELSE
}