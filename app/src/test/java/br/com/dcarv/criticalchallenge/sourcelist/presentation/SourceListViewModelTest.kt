package br.com.dcarv.criticalchallenge.sourcelist.presentation

import br.com.dcarv.criticalchallenge.R
import br.com.dcarv.criticalchallenge.common.presentation.StringResourceProvider
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline
import br.com.dcarv.criticalchallenge.sourcelist.domain.usecase.GetHeadlinesBySourceUseCase
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
        every { get(R.string.source_bbc_news_id) } returns SOURCE_ID
        every { get(R.string.source_bbc_news_label) } returns SOURCE_LABEL
    }
    private val udaChain: SourceListUdaChain = mockk(relaxUnitFun = true)

    private val viewModel = SourceListViewModel(
        getHeadlinesBySourceUseCase,
        stringResourceProvider,
        udaChain,
    )

    @Test
    fun `when initialize, should set source name and show headlines`() = runTest {
        val headline = Headline("title")
        val headlines = listOf(headline)
        coEvery { getHeadlinesBySourceUseCase(SOURCE_ID) } returns headlines

        viewModel.initialize()

        coVerify(ordering = Ordering.SEQUENCE) {
            udaChain.submitMessage(SourceListMessage.SetSourceName(SOURCE_LABEL))
            getHeadlinesBySourceUseCase(SOURCE_ID)
            udaChain.submitMessage(SourceListMessage.ShowHeadlines(headlines))
        }
    }
}
