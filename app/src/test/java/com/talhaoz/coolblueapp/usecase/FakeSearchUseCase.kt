package com.talhaoz.coolblueapp.usecase

import android.accounts.NetworkErrorException
import com.talhaoz.coolblueapp.data.Resource
import com.talhaoz.coolblueapp.domain.usecase.SearchUseCase
import com.talhaoz.coolblueapp.repository.FakeSearchRepository
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException


@HiltAndroidTest
class FakeSearchUseCase {

    private lateinit var searchUseCase : SearchUseCase
    private lateinit var fakeSearchRepository : FakeSearchRepository

    @get:Rule
    var exceptionRule = ExpectedException.none()

    @Before
    fun setup() {
        fakeSearchRepository = FakeSearchRepository()
        searchUseCase = SearchUseCase(fakeSearchRepository)
    }

    @Test
    fun getSearchResultsTest()  {

        fakeSearchRepository.setShouldReturnNetworkError(false)

        runBlocking {

            try {
                searchUseCase.invoke("test",1).collect{

                    when(it) {
                        is Resource.Success -> {
                            val searchModel = it.data

                            assertNotNull(searchModel.currentPage)
                            assertNotNull(searchModel.pageCount)
                            assertNotNull(searchModel.pageSize)
                            assertNotNull(searchModel.products)
                            assertNotNull(searchModel.totalResults)

                            assertEquals(searchModel.totalResults, 24)
                        }
                    }

                }
            } catch (e: Exception) {
                exceptionRule.expect(NetworkErrorException::class.java)
            }

        }

    }

}