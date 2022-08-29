package com.talhaoz.coolblueapp.domain.usecase

import com.talhaoz.coolblueapp.data.Resource
import com.talhaoz.coolblueapp.data.model.SearchModel
import com.talhaoz.coolblueapp.domain.repository.SearchRepository
import com.talhaoz.coolblueapp.viewmodel.FailedType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private var searchRepository: SearchRepository
) {
    operator fun invoke(query: String, page: Int): Flow<Resource<SearchModel>> = flow {
        try {
            emit(Resource.loading())
            emit(
                Resource.success(
                    searchRepository.getSearchResults(query, page)
                )
            )
        } catch (e: Exception) {
            emit(Resource.failed(e.localizedMessage.orEmpty(), FailedType.ELSE))
        }
    }
}