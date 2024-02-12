package br.com.dcarv.criticalchallenge.listanddetails.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.dcarv.criticalchallenge.R
import br.com.dcarv.criticalchallenge.common.compose.InitializeOnce
import br.com.dcarv.criticalchallenge.headlinedetails.presentation.HeadlineDetailsScreen
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline
import br.com.dcarv.criticalchallenge.sourcelist.presentation.SourceListScreen

@Composable
fun ListAndDetailsScreen(
    modifier: Modifier = Modifier,
    isCompactWindowSize: Boolean = false,
    listAndDetailsViewModel: ListAndDetailsViewModel = hiltViewModel(),
) {
    InitializeOnce {
        listAndDetailsViewModel.initialize()
    }

    val state = listAndDetailsViewModel.state().value

    ListAndDetailsScreen(
        state = state,
        modifier = modifier,
        isCompactWindowSize = isCompactWindowSize,
        onNavigateToHeadline = listAndDetailsViewModel::onNavigateToHeadline,
        onNavigateBackToList = listAndDetailsViewModel::onNavigateBackToList,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ListAndDetailsScreen(
    state: ListAndDetailsViewState,
    modifier: Modifier = Modifier,
    isCompactWindowSize: Boolean = false,
    onNavigateToHeadline: (Headline) -> Unit = {},
    onNavigateBackToList: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = state.sourceName) },
            )
        },
        modifier = modifier,
    ) { padding ->
        val contentModifier = modifier.padding(padding)
        val selectedHeadline = state.selectedHeadline

        if (isCompactWindowSize) {
            // show list/details one at a time
            if (selectedHeadline == null) {
                SourceListScreen(
                    modifier = contentModifier,
                    onNavigationToHeadline = onNavigateToHeadline,
                )
            } else {
                HeadlineDetailsScreen(headline = selectedHeadline, modifier = contentModifier)
                BackHandler { onNavigateBackToList() }
            }
        } else {
            // show both list and details
            ListAndDetailsContent(
                modifier = contentModifier,
                selectedHeadline = selectedHeadline,
                onNavigateToHeadline = onNavigateToHeadline,
            )
        }
    }
}

@Composable
private fun ListAndDetailsContent(
    modifier: Modifier = Modifier,
    selectedHeadline: Headline? = null,
    onNavigateToHeadline: (Headline) -> Unit = {},
) {
    val listWeight = 1f
    val detailsWeight = 2f
    Row(modifier) {
        SourceListScreen(
            modifier = Modifier.weight(listWeight),
            onNavigationToHeadline = onNavigateToHeadline,
        )
        if (selectedHeadline != null) {
            HeadlineDetailsScreen(headline = selectedHeadline, modifier = Modifier.weight(detailsWeight))
        } else {
            NoSelection(modifier = Modifier.weight(detailsWeight))
        }
    }
}

@Composable
private fun NoSelection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = stringResource(id = R.string.details_no_selection),)
    }
}
