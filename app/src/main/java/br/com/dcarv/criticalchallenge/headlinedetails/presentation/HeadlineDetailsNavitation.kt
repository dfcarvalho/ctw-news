package br.com.dcarv.criticalchallenge.headlinedetails.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline

fun NavGraphBuilder.headlineDetailsGraph(navController: NavController) {
    composable("headlineDetails") {
        val headline = navController.previousBackStackEntry?.savedStateHandle?.get<Headline>("headline")

        headline?.let {
            HeadlineDetailsScreen(it)
        }
        // TODO: handle error
    }
}

