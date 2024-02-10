package br.com.dcarv.criticalchallenge.sourcelist.data.model

import java.time.LocalDateTime

data class HeadlinesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) {

    data class Article(
        val title: String,
        val publishedAt: LocalDateTime,
    )
}

