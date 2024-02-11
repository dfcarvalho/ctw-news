package br.com.dcarv.criticalchallenge.headlinedetails.presentation

import br.com.dcarv.criticalchallenge.common.presentation.UdaChain
import br.com.dcarv.criticalchallenge.common.presentation.UdaViewModel
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

typealias HeadlineDetailsUdaChain = UdaChain<HeadlineDetailsMessage, HeadlineDetailsViewState, HeadlineDetailsViewEvent>

sealed interface HeadlineDetailsMessage {

    data class SetHeadline(val headline: Headline) : HeadlineDetailsMessage
}

sealed interface HeadlineDetailsViewEvent

@HiltViewModel
class HeadlineDetailsViewModel @Inject constructor(
    udaChain: HeadlineDetailsUdaChain
): UdaViewModel<HeadlineDetailsMessage, HeadlineDetailsViewState, HeadlineDetailsViewEvent>(udaChain) {

    fun initialize(headline: Headline) {
        submitMessage(HeadlineDetailsMessage.SetHeadline(headline))
    }
}
