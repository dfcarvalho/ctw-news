package br.com.dcarv.criticalchallenge.sourcelist.data

import br.com.dcarv.criticalchallenge.sourcelist.data.model.HeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getBySource(@Query("sources") source: String): HeadlinesResponse
}
