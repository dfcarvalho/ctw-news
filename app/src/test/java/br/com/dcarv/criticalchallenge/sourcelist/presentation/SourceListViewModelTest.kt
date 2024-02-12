package br.com.dcarv.criticalchallenge.sourcelist.presentation

import br.com.dcarv.criticalchallenge.R
import br.com.dcarv.criticalchallenge.common.presentation.StringResourceProvider
import br.com.dcarv.criticalchallenge.sourcelist.domain.usecase.GetHeadlinesBySourceUseCase
import br.com.dcarv.criticalchallenge.utils.HeadlineObjectProvider
import br.com.dcarv.criticalchallenge.utils.MainDispatcherRule
import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

private const val SOURCE_ID = "source_id"
private const val SOURCE_LABEL = "source label"

class SourceListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getHeadlinesBySourceUseCase: GetHeadlinesBySourceUseCase = mockk()
    private val stringResourceProvider: StringResourceProvider = mockk {
        every { get(R.string.news_source_id) } returns SOURCE_ID
        every { get(R.string.news_source_label) } returns SOURCE_LABEL
    }
    private val udaChain: SourceListUdaChain = mockk(relaxUnitFun = true)

    private val viewModel = SourceListViewModel(
        getHeadlinesBySourceUseCase,
        stringResourceProvider,
        udaChain,
    )

    @Test
    fun `when initialize, should set source name and show headlines ordered by date`() = runTest {
        val unorderedHeadlines = HeadlineObjectProvider.getUnorderedHeadlinesList()
        coEvery { getHeadlinesBySourceUseCase(SOURCE_ID) } returns unorderedHeadlines
        val orderedHeadlines = HeadlineObjectProvider.getOrderedHeadlinesList()

        viewModel.initialize()

        coVerify(ordering = Ordering.SEQUENCE) {
            udaChain.submitMessage(SourceListMessage.SetSourceName(SOURCE_LABEL))
            getHeadlinesBySourceUseCase(SOURCE_ID)
            udaChain.submitMessage(SourceListMessage.ShowHeadlines(orderedHeadlines))
        }
    }

    @Test
    fun `when headline clicked, should submit event to open headline`() = runTest {
        val headline = HeadlineObjectProvider.getHeadline()

        viewModel.onHeadlineClick(headline)

        coVerify { udaChain.submitEvent(SourceListViewEvent.OpenHeadline(headline)) }
    }
}
