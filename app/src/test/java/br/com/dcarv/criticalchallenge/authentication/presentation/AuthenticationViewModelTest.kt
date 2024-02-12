package br.com.dcarv.criticalchallenge.authentication.presentation

import androidx.fragment.app.FragmentActivity
import br.com.dcarv.criticalchallenge.authentication.data.AndroidBiometric
import br.com.dcarv.criticalchallenge.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class AuthenticationViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val androidBiometric: AndroidBiometric = mockk()
    private val udaChain: AuthenticationUdaChain = mockk(relaxUnitFun = true)

    private val viewModel = AuthenticationViewModel(androidBiometric, udaChain)

    @Test
    fun `when initialized and device has no fingerprint sensor, should skip authentication`() = runTest {
        val activity: FragmentActivity = mockk()
        coEvery { androidBiometric.canAuthenticateWithFingerprint() } returns false

        viewModel.initialize(activity)

        coVerify {
            udaChain.submitEvent(AuthenticationViewEvent.NavigateToSourceList)
        }
    }

    @Test
    fun `when initialized and authentication successful, should navigate to source list`() = runTest {
        val activity: FragmentActivity = mockk()
        coEvery { androidBiometric.canAuthenticateWithFingerprint() } returns true
        coEvery { androidBiometric.authenticate(activity) } returns true

        viewModel.initialize(activity)

        coVerify {
            udaChain.submitEvent(AuthenticationViewEvent.NavigateToSourceList)
        }
    }

    @Test
    fun `when initialized and authentication fails, should show error`() = runTest {
        val activity: FragmentActivity = mockk()
        coEvery { androidBiometric.canAuthenticateWithFingerprint() } returns true
        coEvery { androidBiometric.authenticate(activity) } returns false

        viewModel.initialize(activity)

        coVerify {
            udaChain.submitMessage(AuthenticationMessage.ShowAuthError)
        }
    }

    @Test
    fun `when authentication button clicked, should try to authenticate`() = runTest {
        val activity: FragmentActivity = mockk()
        coEvery { androidBiometric.canAuthenticateWithFingerprint() } returns true
        coEvery { androidBiometric.authenticate(activity) } returns true

        viewModel.tryToAuthenticate(activity)

        coVerify {
            androidBiometric.authenticate(activity)
            udaChain.submitEvent(AuthenticationViewEvent.NavigateToSourceList)
        }
    }
}
