package br.com.dcarv.criticalchallenge.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object DispatchersProvider {
    var Main: CoroutineDispatcher = Dispatchers.Main
    var IO: CoroutineDispatcher = Dispatchers.IO
    var Default: CoroutineDispatcher = Dispatchers.Default
    var Unconfined: CoroutineDispatcher = Dispatchers.Unconfined

    fun resetAll() {
        Main = Dispatchers.Main
        IO = Dispatchers.IO
        Default = Dispatchers.Default
        Unconfined = Dispatchers.Unconfined
    }
}
