package br.com.dcarv.criticalchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.dcarv.criticalchallenge.common.theme.CriticalChallengeTheme
import br.com.dcarv.criticalchallenge.headlinedetails.presentation.headlineDetailsGraph
import br.com.dcarv.criticalchallenge.sourcelist.presentation.sourceList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CriticalChallengeTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "sourceList") {
                    sourceList(
                        onNavigationToHeadline = { headline ->
                            navController.currentBackStackEntry?.savedStateHandle?.set("headline", headline)
                            navController.navigate("headlineDetails")
                        }
                    )
                    headlineDetailsGraph(navController)
                }
            }
        }
    }
}
