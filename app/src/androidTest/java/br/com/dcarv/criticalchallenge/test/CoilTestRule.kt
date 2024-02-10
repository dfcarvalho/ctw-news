package br.com.dcarv.criticalchallenge.test

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.test.core.app.ApplicationProvider
import coil.Coil
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.test.FakeImageLoaderEngine
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@OptIn(ExperimentalCoilApi::class)
class CoilTestRule : TestRule {

    private val engine = FakeImageLoaderEngine.Builder()
        .default(ColorDrawable(Color.BLACK))
        .build()

    private val imageLoader = ImageLoader.Builder(ApplicationProvider.getApplicationContext())
        .components { add(engine) }
        .build()

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                Coil.setImageLoader(imageLoader)

                try {
                    base?.evaluate()
                } finally {
                    Coil.reset()
                }
            }
        }
    }
}
