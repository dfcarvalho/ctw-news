package br.com.dcarv.criticalchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.dcarv.criticalchallenge.common.theme.CriticalChallengeTheme
import br.com.dcarv.criticalchallenge.sourcelist.presentation.SourceListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CriticalChallengeTheme {
                SourceListScreen()
            }
        }
    }
}
