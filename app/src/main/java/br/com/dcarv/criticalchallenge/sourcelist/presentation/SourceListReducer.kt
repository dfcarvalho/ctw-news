package br.com.dcarv.criticalchallenge.sourcelist.presentation

import br.com.dcarv.criticalchallenge.common.presentation.UdaReducer
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline
import javax.inject.Inject

data class SourceListViewState(
    val isLoading: Boolean = true,
    val sourceName: String = "",
    val headlines: List<Headline> = emptyList(),
)

class SourceListReducer @Inject constructor() : UdaReducer<SourceListViewState, SourceListMessage> {

    override fun apply(currentState: SourceListViewState, message: SourceListMessage): SourceListViewState {
        return when (message) {
            is SourceListMessage.ShowLoading -> currentState.copy(isLoading = true)
            is SourceListMessage.SetSourceName -> currentState.copy(sourceName = message.sourceName)
            is SourceListMessage.ShowHeadlines -> currentState.copy(headlines = message.headlines, isLoading = false)
        }
    }
}
