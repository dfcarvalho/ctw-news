package br.com.dcarv.criticalchallenge.headlinedetails.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline

private const val HEADLINE_DETAILS_ROUTE = "headlineDetails"

fun NavGraphBuilder.headlineDetailsGraph(navController: NavController) {
    composable(HEADLINE_DETAILS_ROUTE) {
        val headline = navController.previousBackStackEntry?.savedStateHandle?.get<Headline>("headline")

        headline?.let {
            HeadlineDetailsScreen(it)
        }
        // TODO: handle error
    }
}

fun NavController.navigateToHeadlineDetails(headline: Headline) {
    currentBackStackEntry?.savedStateHandle?.set("headline", headline)
    navigate(HEADLINE_DETAILS_ROUTE)
}
