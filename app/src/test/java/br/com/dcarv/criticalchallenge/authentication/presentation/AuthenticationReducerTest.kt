package br.com.dcarv.criticalchallenge.authentication.presentation

import org.junit.Test

class AuthenticationReducerTest {

    private val reducer: AuthenticationReducer = AuthenticationReducer()

    @Test
    fun `when message is ShowAuthError, should show error state`() {
        val currentState = AuthenticationViewState()
        val message = AuthenticationMessage.ShowAuthError
        val expectedState = currentState.copy(authState = AuthState.ERROR)

        val newState = reducer.apply(currentState, message)

        assert(newState == expectedState)
    }
}
