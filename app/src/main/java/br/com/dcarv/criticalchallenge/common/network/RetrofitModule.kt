package br.com.dcarv.criticalchallenge.common.network

import com.squareup.moshi.Moshi
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
    fun provideOkHttpClient(
        authenticationInterceptor: AuthenticationInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authenticationInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(LocalDateTimeJsonAdapter())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        @NewsApiServiceUrl newsApiServiceUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(newsApiServiceUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}
