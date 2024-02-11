package br.com.dcarv.criticalchallenge.headlinedetails.presentation

import br.com.dcarv.criticalchallenge.utils.HeadlineObjectProvider
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class HeadlineDetailsViewModelTest {

    private val udaChain: HeadlineDetailsUdaChain = mockk(relaxUnitFun = true)

    private val viewModel = HeadlineDetailsViewModel(udaChain)

    @Test
    fun `when initialize, should set headline`() {
        val headline = HeadlineObjectProvider.getHeadline()

        viewModel.initialize(headline)

        verify {
            udaChain.submitMessage(HeadlineDetailsMessage.SetHeadline(headline))
        }
    }
}
