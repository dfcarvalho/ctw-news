package br.com.dcarv.criticalchallenge.common.network

import br.com.dcarv.criticalchallenge.sourcelist.data.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsApiServiceApiKey

@Module
@InstallIn(SingletonComponent::class)
class NewsApiModule {

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService = retrofit.create(NewsApiService::class.java)

    @Provides
    @Singleton
    @NewsApiServiceApiKey
    fun provideNewsApiServiceApiKey(): String = "208f7cb0915c42baa72754047f2a2221"
}
