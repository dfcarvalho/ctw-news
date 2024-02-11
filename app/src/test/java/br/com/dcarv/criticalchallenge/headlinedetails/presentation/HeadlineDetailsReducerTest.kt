package br.com.dcarv.criticalchallenge.headlinedetails.presentation

import br.com.dcarv.criticalchallenge.utils.HeadlineObjectProvider
import org.junit.Test

class HeadlineDetailsReducerTest {

    private val reducer = HeadlineDetailsReducer()

    @Test
    fun `when message is SetHeadline, should set headline and loading to false`() {
        val headline = HeadlineObjectProvider.getHeadline()
        val currentState = HeadlineDetailsViewState(isLoading = true)
        val message = HeadlineDetailsMessage.SetHeadline(headline)
        val expectedState = HeadlineDetailsViewState(headline = headline, isLoading = false)

        val result = reducer.apply(currentState, message)

        assert(expectedState == result) { "Expected $expectedState but was $result" }
    }
}
