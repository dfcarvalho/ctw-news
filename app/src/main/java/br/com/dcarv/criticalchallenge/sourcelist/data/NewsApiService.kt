package br.com.dcarv.criticalchallenge.sourcelist.data

import br.com.dcarv.criticalchallenge.sourcelist.data.model.HeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getBySource(
        // TODO: create interceptor to inject the API key as header
        @Query("apiKey") apiKey: String = "208f7cb0915c42baa72754047f2a2221",
        @Query("sources") source: String
    ): HeadlinesResponse
}
