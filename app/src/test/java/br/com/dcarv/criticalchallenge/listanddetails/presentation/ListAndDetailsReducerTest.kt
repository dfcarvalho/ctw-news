package br.com.dcarv.criticalchallenge.listanddetails.presentation

import br.com.dcarv.criticalchallenge.utils.HeadlineObjectProvider
import org.junit.Test

class ListAndDetailsReducerTest {

    private val reducer: ListAndDetailsReducer = ListAndDetailsReducer()

    @Test
    fun `when message is SetSourceName, should set source name`() {
        val currentState = ListAndDetailsViewState(sourceName = "")
        val message = ListAndDetailsMessage.SetSourceName("CNN")
        val expectedState = ListAndDetailsViewState(sourceName = "CNN")

        val result = reducer.apply(currentState, message)

        assert(expectedState == result) { "Expected $expectedState but was $result" }
    }

    @Test
    fun `when message is ShowHeadlineDetails, should show headline details`() {
        val currentState = ListAndDetailsViewState()
        val headline = HeadlineObjectProvider.getHeadline()
        val message = ListAndDetailsMessage.ShowHeadlineDetails(headline)
        val expectedState = currentState.copy(selectedHeadline = message.headline)

        val newState = reducer.apply(currentState, message)

        assert(newState == expectedState)
    }

    @Test
    fun `when message is HideHeadlineDetails, should hide headline details`() {
        val headline = HeadlineObjectProvider.getHeadline()
        val currentState = ListAndDetailsViewState(selectedHeadline = headline)
        val message = ListAndDetailsMessage.HideHeadlineDetails
        val expectedState = currentState.copy(selectedHeadline = null)

        val newState = reducer.apply(currentState, message)

        assert(newState == expectedState)
    }
}
