package br.com.dcarv.criticalchallenge.authentication.presentation

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val AUTHENTICATION_ROUTE = "authentication"

fun NavGraphBuilder.authentication(
    activity: FragmentActivity,
    onNavigateToSourceList: () -> Unit,
) {
    composable(AUTHENTICATION_ROUTE) {
        AuthenticationScreen(activity = activity, onNavigateToSourceList = onNavigateToSourceList)
    }
}
