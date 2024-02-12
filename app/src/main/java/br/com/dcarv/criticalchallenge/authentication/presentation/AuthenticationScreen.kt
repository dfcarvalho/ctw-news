package br.com.dcarv.criticalchallenge.authentication.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.dcarv.criticalchallenge.R
import br.com.dcarv.criticalchallenge.common.compose.InitializeOnce
import br.com.dcarv.criticalchallenge.common.compose.ObserveEvents
import br.com.dcarv.criticalchallenge.common.presentation.LoadingIndicator
import br.com.dcarv.criticalchallenge.common.theme.CriticalChallengeTheme

@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
    activity: FragmentActivity,
    authenticationViewModel: AuthenticationViewModel = hiltViewModel<AuthenticationViewModel>(),
    onNavigateToSourceList: () -> Unit = {},
) {

    InitializeOnce {
        authenticationViewModel.initialize(activity)
    }

    ObserveEvents(authenticationViewModel.events()) {
        when (it) {
            is AuthenticationViewEvent.NavigateToSourceList -> onNavigateToSourceList()
        }
    }

    AuthenticationScreen(
        state = authenticationViewModel.state().value,
        modifier = modifier,
        onAuthButtonClicked = { authenticationViewModel.tryToAuthenticate(activity) },
    )
}

@Composable
internal fun AuthenticationScreen(
    state: AuthenticationViewState,
    modifier: Modifier = Modifier,
    onAuthButtonClicked: () -> Unit = {},
) {

    when (state.authState) {
        AuthState.AUTHENTICATING -> LoadingIndicator(modifier = modifier)
        AuthState.ERROR -> AuthenticationError(
            modifier = modifier,
            onAuthButtonClicked = onAuthButtonClicked,
        )
    }
}

@Composable
private fun AuthenticationError(
    modifier: Modifier = Modifier,
    onAuthButtonClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(id = R.string.auth_error))
        Button(
            onClick = onAuthButtonClicked,
            modifier = Modifier.padding(top = 12.dp),
        ) {
            Text(text = stringResource(id = R.string.auth_button_label))
        }
    }
}

@Preview(showBackground = true, apiLevel = 33)
@Composable
fun AuthenticationScreenPreviewError() {
    CriticalChallengeTheme {
        AuthenticationScreen(
            state = AuthenticationViewState(authState = AuthState.ERROR),
        )
    }
}
