package br.com.dcarv.criticalchallenge.sourcelist.domain.usecase

import br.com.dcarv.criticalchallenge.sourcelist.data.NewsApiService
import br.com.dcarv.criticalchallenge.sourcelist.data.model.HeadlinesResponse
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline
import javax.inject.Inject

class GetHeadlinesBySourceUseCase @Inject constructor(
    // For a "cleaner" architecture we could have a Repository interface instead of the Retrofit service here directly,
    // which could map the response to the Headline domain model in the data layer.
    private val service: NewsApiService,
) {

    suspend operator fun invoke(source: String): List<Headline> {
        val headlinesResponse = service.getBySource(source = source)

        return headlinesResponse.toHeadlineModel()
    }
}

fun HeadlinesResponse.toHeadlineModel(): List<Headline> {
    return articles.map { articleResponse ->
        Headline(title = articleResponse.title)
    }
}
