package br.com.dcarv.criticalchallenge.sourcelist.domain.usecase

import br.com.dcarv.criticalchallenge.sourcelist.data.NewsApiService
import br.com.dcarv.criticalchallenge.sourcelist.data.model.HeadlinesResponse
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDateTime

class GetHeadlinesBySourceUseCaseTest {

    private val newsApiService: NewsApiService = mockk()

    private val useCase = GetHeadlinesBySourceUseCase(newsApiService)

    @Test
    fun `when when executed with success, should call service and map response to domain model`() = runTest {
        val source = "source"
        val articleTime = LocalDateTime.now()
        val response = HeadlinesResponse(
            status = "ok",
            totalResults = 1,
            articles = listOf(
                HeadlinesResponse.Article(
                    title = "title",
                    publishedAt = articleTime,
                    urlToImage = "url",
                ),
                HeadlinesResponse.Article(
                    title = "title",
                    publishedAt = articleTime,
                    urlToImage = null,
                ),
            )
        )
        val expectedResult = listOf(
            Headline(
                title = "title",
                date = articleTime,
                imageUrl = "url",
            ),
            Headline(
                title = "title",
                date = articleTime,
                imageUrl = null,
            ),
        )
        coEvery { newsApiService.getBySource(source = source) } returns response

        val result = useCase(source)

        assert(expectedResult == result) { "Expected $expectedResult but was $result" }
        coVerify { newsApiService.getBySource(source = source) }
    }

    @Test
    fun `when executed with error, should throw exception`() = runTest {
        val source = "source"
        val error = Exception("Some error")
        coEvery { newsApiService.getBySource(source = source) } throws error

        try {
            useCase(source)
        } catch (e: Exception) {
            assert(e == error) { "Expected $error but was $e" }
            return@runTest
        }

        assert(false) { "Expected exception" }
    }
}
