package br.com.dcarv.criticalchallenge.common.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag(LoadingIndicatorTestTag.LOADING_INDICATOR)
        )
    }
}

internal object LoadingIndicatorTestTag {

    const val LOADING_INDICATOR = "loading-indicator"
}
