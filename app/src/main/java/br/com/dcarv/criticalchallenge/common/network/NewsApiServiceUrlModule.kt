package br.com.dcarv.criticalchallenge.common.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsApiServiceUrl

@Module
@InstallIn(SingletonComponent::class)
class NewsApiServiceUrlModule {

    @Provides
    @Singleton
    @NewsApiServiceUrl
    fun provideNewsApiServiceUrl(): String = "https://newsapi.org/v2/"
}
