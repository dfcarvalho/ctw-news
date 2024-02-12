package br.com.dcarv.criticalchallenge.listanddetails.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.dcarv.criticalchallenge.authentication.presentation.AUTHENTICATION_ROUTE

private const val LIST_AND_DETAILS_ROUTE = "listAndDetails"

fun NavGraphBuilder.listAndDetails(isCompactSize: Boolean) {
    composable(LIST_AND_DETAILS_ROUTE) {
        ListAndDetailsScreen(isCompactWindowSize = isCompactSize)
    }
}

fun NavController.navigateToListAndDetails() {
    navigate(LIST_AND_DETAILS_ROUTE) {
        popUpTo(AUTHENTICATION_ROUTE) { inclusive = true }
    }
}
