package br.com.dcarv.criticalchallenge.listanddetails.presentation

import br.com.dcarv.criticalchallenge.R
import br.com.dcarv.criticalchallenge.common.presentation.StringResourceProvider
import br.com.dcarv.criticalchallenge.common.presentation.UdaChain
import br.com.dcarv.criticalchallenge.common.presentation.UdaViewModel
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

typealias ListAndDetailsUdaChain = UdaChain<ListAndDetailsMessage, ListAndDetailsViewState, ListAndDetailsViewEvent>

sealed interface ListAndDetailsMessage {
    data class SetSourceName(val sourceName: String) : ListAndDetailsMessage
    data class ShowHeadlineDetails(val headline: Headline) : ListAndDetailsMessage
    data object HideHeadlineDetails : ListAndDetailsMessage
}

sealed interface ListAndDetailsViewEvent

@HiltViewModel
class ListAndDetailsViewModel @Inject constructor(
    private val stringResourceProvider: StringResourceProvider,
    udaChain: ListAndDetailsUdaChain,
) : UdaViewModel<ListAndDetailsMessage, ListAndDetailsViewState, ListAndDetailsViewEvent>(udaChain) {

    fun initialize() {
        submitMessage(ListAndDetailsMessage.SetSourceName(stringResourceProvider.get(R.string.news_source_label)))
    }

    fun onNavigateToHeadline(headline: Headline) {
        submitMessage(ListAndDetailsMessage.ShowHeadlineDetails(headline))
    }

    fun onNavigateBackToList() {
        submitMessage(ListAndDetailsMessage.HideHeadlineDetails)
    }
}
