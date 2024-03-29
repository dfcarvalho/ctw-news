package br.com.dcarv.criticalchallenge.sourcelist.presentation

import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline
import org.junit.Test
import java.time.LocalDateTime

class SourceListReducerTest {

    private val reducer = SourceListReducer()

    @Test
    fun `when message is ShowLoading, should set loading to true`() {
        val currentState = SourceListViewState(isLoading = false)
        val message = SourceListMessage.ShowLoading
        val expectedState = SourceListViewState(isLoading = true)

        val result = reducer.apply(currentState, message)

        assert(expectedState == result) { "Expected $expectedState but was $result" }
    }

    @Test
    fun `when message is ShowHeadlines, should set headlines and loading to false`() {
        val headline = Headline(title = "title", date = LocalDateTime.now())
        val currentState = SourceListViewState(headlines = emptyList(), isLoading = true)
        val message = SourceListMessage.ShowHeadlines(listOf(headline))
        val expectedState = SourceListViewState(headlines = listOf(headline), isLoading = false)

        val result = reducer.apply(currentState, message)

        assert(expectedState == result) { "Expected $expectedState but was $result" }
    }
}
