package br.com.dcarv.criticalchallenge.sourcelist.presentation

import br.com.dcarv.criticalchallenge.common.presentation.UdaChain
import br.com.dcarv.criticalchallenge.common.presentation.UdaViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

typealias SourceListUdaChain = UdaChain<SourceListMessage, SourceListViewState, SourceListViewEvent>

sealed interface SourceListMessage {

}

sealed interface SourceListViewEvent {

}

@HiltViewModel
class SourceListViewModel @Inject constructor(
    udaChain: SourceListUdaChain,
) : UdaViewModel<SourceListMessage, SourceListViewState, SourceListViewEvent>(udaChain)
