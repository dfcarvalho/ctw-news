package br.com.dcarv.criticalchallenge.authentication.presentation

import br.com.dcarv.criticalchallenge.common.presentation.UdaReducer
import javax.inject.Inject

data class AuthenticationViewState(
    val authState: AuthState = AuthState.AUTHENTICATING,
)

enum class AuthState {
    AUTHENTICATING,
    ERROR,
}

class AuthenticationReducer @Inject constructor() : UdaReducer<AuthenticationViewState, AuthenticationMessage> {

    override fun apply(currentState: AuthenticationViewState, message: AuthenticationMessage): AuthenticationViewState {
        return when (message) {
            is AuthenticationMessage.ShowAuthError -> currentState.copy(authState = AuthState.ERROR)
        }
    }
}
