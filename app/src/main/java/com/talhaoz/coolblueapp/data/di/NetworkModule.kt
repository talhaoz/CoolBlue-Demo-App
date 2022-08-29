package com.talhaoz.coolblueapp.data.di

import com.talhaoz.coolblueapp.data.remote.SearchApi
import com.talhaoz.coolblueapp.data.repository.SearchRepositoryImpl
import com.talhaoz.coolblueapp.domain.repository.SearchRepository
import com.talhaoz.coolblueapp.domain.usecase.SearchUseCase
import com.talhaoz.coolblueapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): SearchApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(getHttpClient())
            .build()
            .create(SearchApi::class.java)
    }

    private fun getHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideSearchRepository(
        searchApi: SearchApi
    ): SearchRepository = SearchRepositoryImpl(searchApi)

    @Singleton
    @Provides
    fun provideSearchRepositoryImpl(
        searchApi: SearchApi
    ): SearchRepositoryImpl = SearchRepositoryImpl(searchApi)

    @Singleton
    @Provides
    fun provideSearchUseCase(
        searchRepository: SearchRepository
    ): SearchUseCase = SearchUseCase(searchRepository)
}