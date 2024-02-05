package br.com.dcarv.criticalchallenge.test

import br.com.dcarv.criticalchallenge.common.network.NewsApiServiceUrl
import br.com.dcarv.criticalchallenge.common.network.NewsApiServiceUrlModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NewsApiServiceUrlModule::class],
)
class TestNewsApiServiceUrlModule {

    @Provides
    @Singleton
    @NewsApiServiceUrl
    fun provideNewsApiServiceUrl(): String = "http://127.0.0.1:8181"
}
