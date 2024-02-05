package br.com.dcarv.criticalchallenge.sourcelist.data.model

data class HeadlinesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) {

    data class Article(
        val title: String
    )
}

