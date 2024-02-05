package br.com.dcarv.criticalchallenge.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import br.com.dcarv.criticalchallenge.common.coroutines.DispatchersProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UdaChain<Message : Any, ViewState : Any, ViewEvent : Any>(
    reducer: UdaReducer<ViewState, Message>,
    internal val initialViewState: ViewState
) {

    private val job = Job()
    private val scope = CoroutineScope(job + DispatchersProvider.Main)

    private val messagesFlow = MutableSharedFlow<Message>()
    private val eventsFlow = MutableSharedFlow<ViewEvent>()

    private val statesFlow: Flow<ViewState> = messagesFlow
        .scan(initialViewState, reducer::apply)
        .distinctUntilChanged()
        .shareIn(scope, SharingStarted.Eagerly, replay = 1)

    fun submitMessage(vararg messages: Message) {
        scope.launch {
            messages.forEach { message ->
                messagesFlow.emit(message)
            }
        }
    }

    fun submitEvent(event: ViewEvent) {
        scope.launch {
            eventsFlow.emit(event)
        }
    }

    fun states(): Flow<ViewState> = statesFlow
    fun events(): Flow<ViewEvent> = eventsFlow

    fun dispose() {
        job.cancel()
    }
}

abstract class UdaViewModel<Message : Any, ViewState : Any, ViewEvent : Any>(
    private val udaChain: UdaChain<Message, ViewState, ViewEvent>
) : ViewModel() {

    protected val currentState: ViewState
        get() = runBlocking { udaChain.states().first() }

    protected fun submitMessage(vararg messages: Message) = udaChain.submitMessage(*messages)
    protected fun submitEvent(event: ViewEvent) = udaChain.submitEvent(event)

    @Composable
    fun state() = udaChain.states().collectAsState(initial = udaChain.initialViewState)
    fun events() = udaChain.events()

    override fun onCleared() {
        super.onCleared()
        udaChain.dispose()
    }
}

interface UdaReducer<ViewState : Any, Message : Any> {

    fun apply(currentState: ViewState, message: Message): ViewState
}
