package br.com.dcarv.criticalchallenge.sourcelist.presentation

import br.com.dcarv.criticalchallenge.common.presentation.UdaReducer
import javax.inject.Inject

data class SourceListViewState(
    val isLoading: Boolean = true,
)

class SourceListReducer @Inject constructor() : UdaReducer<SourceListViewState, SourceListMessage> {

    override fun invoke(currentState: SourceListViewState, message: SourceListMessage): SourceListViewState {
        // TODO: apply message to state
        return currentState
    }
}
