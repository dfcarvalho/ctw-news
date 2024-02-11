package br.com.dcarv.criticalchallenge.sourcelist.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline

fun NavGraphBuilder.sourceList(
    onNavigationToHeadline: (Headline) -> Unit
) {
    composable("sourceList") { SourceListScreen(onNavigationToHeadline = onNavigationToHeadline) }
}

