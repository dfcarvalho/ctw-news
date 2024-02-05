package br.com.dcarv.criticalchallenge.common.network

import br.com.dcarv.criticalchallenge.sourcelist.data.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsApiModule {

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService = retrofit.create(NewsApiService::class.java)
}
