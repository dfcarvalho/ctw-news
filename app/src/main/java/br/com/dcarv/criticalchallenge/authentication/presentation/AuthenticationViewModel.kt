package br.com.dcarv.criticalchallenge.authentication.presentation

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewModelScope
import br.com.dcarv.criticalchallenge.authentication.data.AndroidBiometric
import br.com.dcarv.criticalchallenge.common.presentation.UdaChain
import br.com.dcarv.criticalchallenge.common.presentation.UdaViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias AuthenticationUdaChain = UdaChain<AuthenticationMessage, AuthenticationViewState, AuthenticationViewEvent>

sealed interface AuthenticationMessage {

    data object ShowAuthError : AuthenticationMessage
}

sealed interface AuthenticationViewEvent {

    data object NavigateToSourceList : AuthenticationViewEvent
}

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val androidBiometric: AndroidBiometric,
    udaChain: AuthenticationUdaChain,
) : UdaViewModel<AuthenticationMessage, AuthenticationViewState, AuthenticationViewEvent>(udaChain) {

    fun initialize(activity: FragmentActivity) {
        tryToAuthenticate(activity)
    }

    fun tryToAuthenticate(activity: FragmentActivity) = viewModelScope.launch {
        if (androidBiometric.canAuthenticateWithFingerprint()) {
            if (androidBiometric.authenticate(activity)) {
                submitEvent(AuthenticationViewEvent.NavigateToSourceList)
            } else {
                submitMessage(AuthenticationMessage.ShowAuthError)
            }
        } else {
            // fingerprint not available
            submitEvent(AuthenticationViewEvent.NavigateToSourceList)
        }
    }
}
