package br.com.dcarv.criticalchallenge.headlinedetails.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import br.com.dcarv.criticalchallenge.common.presentation.LoadingIndicator
import br.com.dcarv.criticalchallenge.common.theme.CriticalChallengeTheme
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline
import coil.compose.AsyncImage
import java.time.LocalDateTime

@Composable
fun HeadlineDetailsScreen(
    headline: Headline,
    modifier: Modifier = Modifier,
    headlineDetailsViewModel: HeadlineDetailsViewModel = hiltViewModel<HeadlineDetailsViewModel>(),
) {

    InitializeOnce {
        headlineDetailsViewModel.initialize(headline)
    }

    HeadlineDetailsScreen(
        state = headlineDetailsViewModel.state().value,
        modifier = modifier,
    )
}

@Composable
fun HeadlineDetailsScreen(
    state: HeadlineDetailsViewState,
    modifier: Modifier = Modifier,
) {
    println("DFC - composition - $state")
    if (!state.isLoading && state.headline != null) {
        HeadlineDetailsContent(state.headline, modifier)
    } else {
        LoadingIndicator(modifier)
    }
}

@Composable
fun HeadlineDetailsContent(
    headline: Headline,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            model = headline.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 100.dp)
                .align(Alignment.CenterHorizontally),
            placeholder = ColorPainter(MaterialTheme.colorScheme.primary),
        )

        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)) {
            Text(
                text = headline.title,
                modifier = Modifier.padding(top = 12.dp),
                style = MaterialTheme.typography.displaySmall,
            )
            Text(
                text = headline.content,
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
fun HeadlineDetailsScreenPreview() {
    val state = HeadlineDetailsViewState(
        isLoading = false,
        headline = Headline(
            title = "Essex dog attack: Grandmother killed by XL bully dogs, family says - BBC",
            date = LocalDateTime.now(),
        ),
    )
    CriticalChallengeTheme {
        HeadlineDetailsScreen(state = state)
    }
}
