/*
 * Copyright (C) 2016 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.dcarv.criticalchallenge.test

import androidx.annotation.CheckResult
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import kotlin.concurrent.Volatile

/**
 * Adapted from [Jake Wharton's OkHttp3IdlingResource](https://github.com/JakeWharton/okhttp-idling-resource/blob/master/src/main/java/com/jakewharton/espresso/OkHttp3IdlingResource.java).
 * This class is a bridge between OkHttp3 and Espresso. Use it to register OkHttp3 as an idling resource.
 */

class OkHttp3IdlingResource private constructor(private val name: String, private val dispatcher: Dispatcher) : IdlingResource {

    @Volatile
    var callback: ResourceCallback? = null

    init {
        dispatcher.idleCallback = Runnable {
            val callback = callback
            callback?.onTransitionToIdle()
        }
    }

    override fun isIdleNow(): Boolean = dispatcher.runningCallsCount() == 0

    override fun getName() = name

    override fun registerIdleTransitionCallback(callback: ResourceCallback?) {
        this.callback = callback
    }

    companion object {

        /**
         * Create a new [IdlingResource] from `client` as `name`. You must register
         * this instance using `Espresso.registerIdlingResources`.
         */
        @CheckResult
        fun create(name: String, client: OkHttpClient): OkHttp3IdlingResource {
            return OkHttp3IdlingResource(name, client.dispatcher)
        }
    }
}
