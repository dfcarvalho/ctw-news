package br.com.dcarv.criticalchallenge.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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

@Composable
fun <ViewEvent> ObserveEvents(
    events: Flow<ViewEvent>,
    onEach: (ViewEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    DisposableEffect(events) {
        val job = coroutineScope.launch {
            events.collect(onEach)
        }

        onDispose {
            job.cancel()
        }
    }
}
