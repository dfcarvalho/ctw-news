package br.com.dcarv.criticalchallenge.authentication.presentation

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import br.com.dcarv.criticalchallenge.R
import br.com.dcarv.criticalchallenge.common.presentation.LoadingIndicatorTestTag
import org.junit.Rule
import org.junit.Test

class AuthenticationScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun whenAuthenticatingStateShouldShowLoading() {
        val state = AuthenticationViewState(authState = AuthState.AUTHENTICATING)

        renderState(state)

        composeTestRule.onNodeWithTag(LoadingIndicatorTestTag.LOADING_INDICATOR).assertExists()
    }

    @Test
    fun whenErrorStateShouldShowErrorMessageAndAuthButton() {
        val state = AuthenticationViewState(authState = AuthState.ERROR)

        renderState(state)

        composeTestRule.onNodeWithTag(LoadingIndicatorTestTag.LOADING_INDICATOR).assertDoesNotExist()
        composeTestRule.onNodeWithText(context.getString(R.string.auth_error)).assertExists()
        composeTestRule.onNodeWithText(context.getString(R.string.auth_button_label)).assertExists()
    }

    private fun renderState(state: AuthenticationViewState) {
        composeTestRule.setContent {
            AuthenticationScreen(state)
        }
    }
}
