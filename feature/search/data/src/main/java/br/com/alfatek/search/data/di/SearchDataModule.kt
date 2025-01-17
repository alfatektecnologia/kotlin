package br.com.alfatek.search.data.di

import br.com.alfatek.search.data.remote.SearchApiService
import br.com.alfatek.search.data.repository.SearchRepoImpl
import br.com.alfatek.search.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://www.themealdb.com/"

@InstallIn(SingletonComponent::class)
@Module
object SearchDataModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideSearchApiService(retrofit: Retrofit): SearchApiService {
        return retrofit.create(SearchApiService::class.java)
    }

    @Provides
    fun provideSearchRepo(searchApiService: SearchApiService): SearchRepository {
        return SearchRepoImpl(searchApiService)
    }

}