package br.com.dcarv.criticalchallenge.sourcelist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dcarv.criticalchallenge.common.compose.InitializeOnce
import br.com.dcarv.criticalchallenge.common.theme.CriticalChallengeTheme
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline

@Composable
fun SourceListScreen(
    modifier: Modifier = Modifier,
    sourceListViewModel: SourceListViewModel = viewModel<SourceListViewModel>()
) {
    InitializeOnce {
        sourceListViewModel.initialize()
    }

    SourceListScreen(
        state = sourceListViewModel.state().value,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SourceListScreen(
    state: SourceListViewState,
    modifier: Modifier = Modifier,
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

        if (state.isLoading) {
            LoadingState(contentModifier)
        } else {
            SourceListContent(state = state, modifier = contentModifier)
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag(SourceListScreenTestTag.LOADING_INDICATOR)
        )
    }
}

@Composable
private fun SourceListContent(
    state: SourceListViewState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        NewsList(state.headlines)
    }
}

@Composable
private fun NewsList(news: List<Headline>) {
    LazyColumn(
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(news) { headline ->
            HeadlineContent(headline)
        }
    }
}

@Composable
private fun HeadlineContent(
    headline: Headline,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(all = 12.dp)
        ) {
            Text(text = headline.title)
        }
    }
}

internal object SourceListScreenTestTag {

    const val LOADING_INDICATOR = "source-list-loading-indicator"
}

@Preview(showBackground = true)
@Composable
fun SourceListScreenPreview() {
    CriticalChallengeTheme {
        SourceListScreen(
            state = SourceListViewState(
                isLoading = false,
                sourceName = "BBC News",
                headlines = listOf(
                    Headline(title = "Essex dog attack: Grandmother killed by XL bully dogs, family says - BBC"),
                    Headline(title = "US declines to rule out hitting targets in Iran, Jake Sullivan says - POLITICO"),
                    Headline(title = "Facebook Turns 20: From Mark Zuckerberg's Harvard Dorm Room to the Metaverse - The Wall Street Journal"),
                )
            )
        )
    }
}
