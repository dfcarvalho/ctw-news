package br.com.dcarv.criticalchallenge.headlinedetails.presentation

import br.com.dcarv.criticalchallenge.common.presentation.UdaReducer
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline
import javax.inject.Inject

data class HeadlineDetailsViewState(
    val isLoading: Boolean = true,
    val headline: Headline? = null,
)

class HeadlineDetailsReducer @Inject constructor() : UdaReducer<HeadlineDetailsViewState, HeadlineDetailsMessage> {

    override fun apply(currentState: HeadlineDetailsViewState, message: HeadlineDetailsMessage): HeadlineDetailsViewState {
        return when (message) {
            is HeadlineDetailsMessage.SetHeadline -> currentState.copy(headline = message.headline, isLoading = false)
        }
    }
}
