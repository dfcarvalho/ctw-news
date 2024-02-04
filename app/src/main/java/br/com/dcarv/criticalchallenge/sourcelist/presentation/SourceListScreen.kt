package br.com.dcarv.criticalchallenge.sourcelist.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dcarv.criticalchallenge.common.theme.CriticalChallengeTheme

@Composable
fun SourceListScreen(
    modifier: Modifier = Modifier,
    sourceListViewModel: SourceListViewModel = viewModel<SourceListViewModel>()
) {

    SourceListScreen(
        state = sourceListViewModel.state().value,
        modifier = modifier,
    )
}

@Composable
private fun SourceListScreen(
    state: SourceListViewState,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Hello",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun SourceListScreenPreview() {
    CriticalChallengeTheme {
        SourceListScreen(
            state = SourceListViewState()
        )
    }
}
