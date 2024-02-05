package br.com.dcarv.criticalchallenge.test

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import br.com.dcarv.criticalchallenge.common.coroutines.DispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlin.coroutines.CoroutineContext

/**
 * Adapted from https://github.com/Kotlin/kotlinx.coroutines/issues/242#issuecomment-561503344
 */
class IdlingDispatchersTestRule : TestRule {

    override fun apply(base: Statement?, description: Description?): Statement =
        object : Statement() {
            override fun evaluate() {
                val espressoTrackedDispatcherIO = EspressoTrackedDispatcher(Dispatchers.IO)
                val espressoTrackedDispatcherDefault = EspressoTrackedDispatcher(Dispatchers.Default)
                DispatchersProvider.IO = espressoTrackedDispatcherIO
                DispatchersProvider.Default = espressoTrackedDispatcherDefault

                try {
                    base?.evaluate()
                } finally {
                    espressoTrackedDispatcherIO.cleanUp()
                    espressoTrackedDispatcherDefault.cleanUp()
                    DispatchersProvider.resetAll()
                }
            }
        }
}

class EspressoTrackedDispatcher(private val wrappedCoroutineDispatcher: CoroutineDispatcher) : CoroutineDispatcher() {

    private val counter = CountingIdlingResource("EspressoTrackedDispatcher for $wrappedCoroutineDispatcher")

    init {
        IdlingRegistry.getInstance().register(counter)
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        counter.increment()
        val blockWithDecrement = Runnable {
            try {
                block.run()
            } finally {
                counter.decrement()
            }
        }
        wrappedCoroutineDispatcher.dispatch(context, blockWithDecrement)
    }

    fun cleanUp() {
        IdlingRegistry.getInstance().unregister(counter)
    }
}
