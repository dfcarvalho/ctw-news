package br.com.dcarv.criticalchallenge.sourcelist.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.dcarv.criticalchallenge.common.compose.InitializeOnce
import br.com.dcarv.criticalchallenge.common.compose.ObserveEvents
import br.com.dcarv.criticalchallenge.common.presentation.LoadingIndicator
import br.com.dcarv.criticalchallenge.common.theme.CriticalChallengeTheme
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline
import coil.compose.AsyncImage
import java.time.LocalDateTime

@Composable
fun SourceListScreen(
    modifier: Modifier = Modifier,
    sourceListViewModel: SourceListViewModel = hiltViewModel<SourceListViewModel>(),
    onNavigationToHeadline: (Headline) -> Unit = {},
) {
    InitializeOnce {
        sourceListViewModel.initialize()
    }

    ObserveEvents(sourceListViewModel.events()) {
        when (it) {
            is SourceListViewEvent.OpenHeadline -> onNavigationToHeadline(it.headline)
        }
    }

    SourceListScreen(
        state = sourceListViewModel.state().value,
        modifier = modifier,
        onHeadlineClick = sourceListViewModel::onHeadlineClick,
    )
}

@Composable
internal fun SourceListScreen(
    state: SourceListViewState,
    modifier: Modifier = Modifier,
    onHeadlineClick: (Headline) -> Unit = {},
) {
    if (state.isLoading) {
        LoadingIndicator(modifier)
    } else {
        SourceListContent(
            state = state,
            modifier = modifier,
            onHeadlineClick = onHeadlineClick,
        )
    }
}

@Composable
private fun SourceListContent(
    state: SourceListViewState,
    modifier: Modifier = Modifier,
    onHeadlineClick: (Headline) -> Unit = {},
) {
    Column(
        modifier = modifier,
    ) {
        NewsList(
            news = state.headlines,
            onHeadlineClick = onHeadlineClick,
        )
    }
}

@Composable
private fun NewsList(
    news: List<Headline>,
    onHeadlineClick: (Headline) -> Unit = {},
) {
    LazyColumn(
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(news) { headline ->
            HeadlineContent(headline, onClick = { onHeadlineClick(headline) })
        }
    }
}

@Composable
private fun HeadlineContent(
    headline: Headline,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column {
            AsyncImage(
                model = headline.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 100.dp)
                    .align(Alignment.CenterHorizontally),
                placeholder = ColorPainter(MaterialTheme.colorScheme.primary),
            )
            Text(text = headline.title, modifier = Modifier.padding(all = 12.dp))
        }
    }
}

@Preview(showBackground = true, apiLevel = 33)
@Composable
fun SourceListScreenPreview() {
    CriticalChallengeTheme {
        SourceListScreen(
            state = SourceListViewState(
                isLoading = false,
                headlines = listOf(
                    Headline(
                        title = "Essex dog attack: Grandmother killed by XL bully dogs, family says - BBC",
                        date = LocalDateTime.now(),
                    ),
                    Headline(
                        title = "US declines to rule out hitting targets in Iran, Jake Sullivan says - POLITICO",
                        date = LocalDateTime.now(),
                    ),
                    Headline(
                        title = "Facebook Turns 20: From Mark Zuckerberg's Harvard Dorm Room to the Metaverse - The Wall Street Journal",
                        date = LocalDateTime.now(),
                    ),
                )
            ),
        )
    }
}
