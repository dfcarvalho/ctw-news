package br.com.dcarv.criticalchallenge.sourcelist.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.dcarv.criticalchallenge.authentication.presentation.AUTHENTICATION_ROUTE
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline

private const val SOURCE_LIST_ROUTE = "sourceList"

fun NavGraphBuilder.sourceList(
    onNavigationToHeadline: (Headline) -> Unit
) {
    composable(SOURCE_LIST_ROUTE) { SourceListScreen(onNavigationToHeadline = onNavigationToHeadline) }
}

fun NavController.navigateToSourceList() {
    navigate(SOURCE_LIST_ROUTE) {
        popUpTo(AUTHENTICATION_ROUTE) { inclusive = true }
    }
}
