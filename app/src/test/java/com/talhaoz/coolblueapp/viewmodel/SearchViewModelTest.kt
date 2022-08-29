package com.talhaoz.coolblueapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.talhaoz.coolblue_demo.getOrAwaitValueTest
import com.talhaoz.coolblueapp.data.Resource
import com.talhaoz.coolblueapp.domain.usecase.SearchUseCase
import com.talhaoz.coolblueapp.repository.FakeSearchRepository
import com.talhaoz.coolblueapp.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    private lateinit var searchViewModel: SearchViewModel

    @OptIn(ExperimentalCoroutinesApi::class)

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        searchViewModel = SearchViewModel(SearchUseCase(FakeSearchRepository()))
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun searchProductListWithEmptyQuery() {
        searchViewModel.getSearchResults("")

        when (val resultData = searchViewModel.searchLiveData.getOrAwaitValueTest()) {
            is Resource.Failed -> {
                assertThat(resultData.message).isEqualTo(Constants.SEARCH_QUERY_EMPTY_ERROR)
            }
        }
    }


    @Test
    fun searchProductListWithTooLongQuery() {
        val queryTooLong = Constants.TEST_LONG_QUERY_CONSTANT
        searchViewModel.getSearchResults(queryTooLong)

        when (val resultData = searchViewModel.searchLiveData.getOrAwaitValueTest()) {
            is Resource.Failed -> {
                assertThat(resultData.message).isEqualTo(Constants.SEARCH_QUERY_TOO_LONG_ERROR)
            }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}