package br.com.dcarv.criticalchallenge.listanddetails.presentation

import br.com.dcarv.criticalchallenge.common.presentation.UdaReducer
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline
import javax.inject.Inject

data class ListAndDetailsViewState(
    val sourceName: String = "",
    val selectedHeadline: Headline? = null,
)

class ListAndDetailsReducer @Inject constructor() : UdaReducer<ListAndDetailsViewState, ListAndDetailsMessage> {

    override fun apply(currentState: ListAndDetailsViewState, message: ListAndDetailsMessage): ListAndDetailsViewState {
        return when (message) {
            is ListAndDetailsMessage.SetSourceName -> currentState.copy(sourceName = message.sourceName)
            is ListAndDetailsMessage.ShowHeadlineDetails -> currentState.copy(selectedHeadline = message.headline)
            is ListAndDetailsMessage.HideHeadlineDetails -> currentState.copy(selectedHeadline = null)
        }
    }
}
