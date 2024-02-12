package br.com.dcarv.criticalchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.dcarv.criticalchallenge.authentication.presentation.AUTHENTICATION_ROUTE
import br.com.dcarv.criticalchallenge.authentication.presentation.authentication
import br.com.dcarv.criticalchallenge.common.theme.CriticalChallengeTheme
import br.com.dcarv.criticalchallenge.headlinedetails.presentation.headlineDetailsGraph
import br.com.dcarv.criticalchallenge.headlinedetails.presentation.navigateToHeadlineDetails
import br.com.dcarv.criticalchallenge.sourcelist.presentation.navigateToSourceList
import br.com.dcarv.criticalchallenge.sourcelist.presentation.sourceList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CriticalChallengeTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = AUTHENTICATION_ROUTE) {
                    authentication(
                        activity = this@MainActivity,
                        onNavigateToSourceList = { navController.navigateToSourceList() }
                    )
                    sourceList(
                        onNavigationToHeadline = { headline -> navController.navigateToHeadlineDetails(headline) }
                    )
                    headlineDetailsGraph(navController)
                }
            }
        }
    }
}
