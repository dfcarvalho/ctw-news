package br.com.dcarv.criticalchallenge.sourcelist.presentation

import androidx.lifecycle.viewModelScope
import br.com.dcarv.criticalchallenge.R
import br.com.dcarv.criticalchallenge.common.coroutines.DispatchersProvider
import br.com.dcarv.criticalchallenge.common.presentation.StringResourceProvider
import br.com.dcarv.criticalchallenge.common.presentation.UdaChain
import br.com.dcarv.criticalchallenge.common.presentation.UdaViewModel
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline
import br.com.dcarv.criticalchallenge.sourcelist.domain.usecase.GetHeadlinesBySourceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias SourceListUdaChain = UdaChain<SourceListMessage, SourceListViewState, SourceListViewEvent>

sealed interface SourceListMessage {

    data object ShowLoading : SourceListMessage
    data class SetSourceName(val sourceName: String) : SourceListMessage
    data class ShowHeadlines(val headlines: List<Headline>) : SourceListMessage
}

sealed interface SourceListViewEvent {

    data class OpenHeadline(val headline: Headline) : SourceListViewEvent
}

@HiltViewModel
class SourceListViewModel @Inject constructor(
    private val getHeadlinesBySource: GetHeadlinesBySourceUseCase,
    private val stringResourceProvider: StringResourceProvider,
    udaChain: SourceListUdaChain,
) : UdaViewModel<SourceListMessage, SourceListViewState, SourceListViewEvent>(udaChain) {

    fun initialize() = viewModelScope.launch {
        submitMessage(SourceListMessage.SetSourceName(stringResourceProvider.get(R.string.source_bbc_news_label)))

        val headlines = async(DispatchersProvider.IO) {
            getHeadlinesBySource(stringResourceProvider.get(R.string.source_bbc_news_id))
        }.await()

        val orderedHeadlines = headlines.sortedByDescending { it.date }

        submitMessage(SourceListMessage.ShowHeadlines(orderedHeadlines))
    }

    fun onHeadlineClick(headline: Headline) {
        submitEvent(SourceListViewEvent.OpenHeadline(headline))
    }
}
