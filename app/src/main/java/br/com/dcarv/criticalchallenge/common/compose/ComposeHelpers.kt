package br.com.dcarv.criticalchallenge.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun InitializeOnce(
    initialization: () -> Unit
) {
    val (isInitialized, setIsInitialized) = remember { mutableStateOf(false) }
    if (!isInitialized) {
        initialization()
        setIsInitialized(true)
    }
}
