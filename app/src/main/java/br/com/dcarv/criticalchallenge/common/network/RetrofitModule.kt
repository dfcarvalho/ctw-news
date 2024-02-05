package br.com.dcarv.criticalchallenge.common.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        @NewsApiServiceUrl newsApiServiceUrl: String
    ): Retrofit {
        val moshiConverterFactory = MoshiConverterFactory.create()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(newsApiServiceUrl)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }
}
