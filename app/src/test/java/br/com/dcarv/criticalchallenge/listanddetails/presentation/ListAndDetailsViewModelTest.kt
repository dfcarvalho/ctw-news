package br.com.dcarv.criticalchallenge.listanddetails.presentation

import br.com.dcarv.criticalchallenge.R
import br.com.dcarv.criticalchallenge.common.presentation.StringResourceProvider
import br.com.dcarv.criticalchallenge.utils.HeadlineObjectProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

private const val SOURCE_LABEL = "source label"

class ListAndDetailsViewModelTest {

    private val stringResourceProvider: StringResourceProvider = mockk {
        every { get(R.string.news_source_label) } returns SOURCE_LABEL
    }
    private val udaChain: ListAndDetailsUdaChain = mockk(relaxUnitFun = true)

    private val viewModel = ListAndDetailsViewModel(stringResourceProvider, udaChain)

    @Test
    fun `when initializing, should set source name`() {
        viewModel.initialize()

        verify { udaChain.submitMessage(ListAndDetailsMessage.SetSourceName(SOURCE_LABEL)) }
    }

    @Test
    fun `when navigating to headline, should show headline details`() {
        val headline = HeadlineObjectProvider.getHeadline()

        viewModel.onNavigateToHeadline(headline)

        verify { udaChain.submitMessage(ListAndDetailsMessage.ShowHeadlineDetails(headline)) }
    }

    @Test
    fun `when navigating back to list, should hide headline details`() {
        viewModel.onNavigateBackToList()

        verify { udaChain.submitMessage(ListAndDetailsMessage.HideHeadlineDetails) }
    }
}
