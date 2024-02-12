package br.com.dcarv.criticalchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.dcarv.criticalchallenge.authentication.presentation.AUTHENTICATION_ROUTE
import br.com.dcarv.criticalchallenge.authentication.presentation.authentication
import br.com.dcarv.criticalchallenge.common.theme.CriticalChallengeTheme
import br.com.dcarv.criticalchallenge.listanddetails.presentation.listAndDetails
import br.com.dcarv.criticalchallenge.listanddetails.presentation.navigateToListAndDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            CriticalChallengeTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = AUTHENTICATION_ROUTE) {
                    authentication(
                        activity = this@MainActivity,
                        onNavigateToSourceList = { navController.navigateToListAndDetails() }
                    )
                    listAndDetails(isCompactSize = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact)
                }
            }
        }
    }
}
