package br.com.dcarv.criticalchallenge.authentication.data

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import br.com.dcarv.criticalchallenge.R
import br.com.dcarv.criticalchallenge.common.presentation.StringResourceProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

private const val AUTH_PROMPT_TITLE = "title"
private const val AUTH_PROMPT_SUBTITLE = "subtitle"
private const val AUTH_PROMPT_CANCEL = "cancel"

@RunWith(RobolectricTestRunner::class)
class AndroidBiometricTest {

    private val biometricManager: BiometricManager = mockk()
    private val biometricPromptFactory: BiometricPromptFactory = mockk()
    private val stringResourceProvider: StringResourceProvider = mockk {
        every { get(R.string.auth_prompt_title) } returns AUTH_PROMPT_TITLE
        every { get(R.string.auth_prompt_subtitle) } returns AUTH_PROMPT_SUBTITLE
        every { get(R.string.auth_prompt_cancel) } returns AUTH_PROMPT_CANCEL
    }

    private val androidBiometric = AndroidBiometric(
        biometricManager,
        biometricPromptFactory,
        stringResourceProvider,
    )

    @Test
    fun `when device has fingerprint sensor, should return true`() {
        every {
            biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        } returns BiometricManager.BIOMETRIC_SUCCESS

        val canAuthenticateWithFingerprint = androidBiometric.canAuthenticateWithFingerprint()

        assert(canAuthenticateWithFingerprint)
    }

    @Test
    fun `when device doesn't have fingerprint sensor, should return false`() {
        every {
            biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        } returns BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE

        val canAuthenticateWithFingerprint = androidBiometric.canAuthenticateWithFingerprint()

        assert(!canAuthenticateWithFingerprint)
    }

    @Test
    fun `when authenticating with success, should return true`() = runTest {
        val activity: FragmentActivity = mockk()
        val callbackSlot = slot<BiometricPrompt.AuthenticationCallback>()
        val promptInfoSlot = slot<BiometricPrompt.PromptInfo>()
        val biometricPrompt: BiometricPrompt = mockk {
            every { authenticate(capture(promptInfoSlot)) } answers {
                callbackSlot.captured.onAuthenticationSucceeded(mockk())
            }
        }
        coEvery { biometricPromptFactory.create(activity, capture(callbackSlot)) } returns biometricPrompt

        val authResult = androidBiometric.authenticate(activity)

        assert(authResult)
        assert(promptInfoSlot.captured.title == AUTH_PROMPT_TITLE)
        assert(promptInfoSlot.captured.subtitle == AUTH_PROMPT_SUBTITLE)
        assert(promptInfoSlot.captured.negativeButtonText == AUTH_PROMPT_CANCEL)
    }

    @Test
    fun `when authenticating with error, should return false`() = runTest {
        val activity: FragmentActivity = mockk()
        val callbackSlot = slot<BiometricPrompt.AuthenticationCallback>()
        val promptInfoSlot = slot<BiometricPrompt.PromptInfo>()
        val biometricPrompt: BiometricPrompt = mockk {
            every { authenticate(capture(promptInfoSlot)) } answers {
                callbackSlot.captured.onAuthenticationError(500, "Some error")
            }
        }
        coEvery { biometricPromptFactory.create(activity, capture(callbackSlot)) } returns biometricPrompt

        val authResult = androidBiometric.authenticate(activity)

        assert(!authResult)
        assert(promptInfoSlot.captured.title == AUTH_PROMPT_TITLE)
        assert(promptInfoSlot.captured.subtitle == AUTH_PROMPT_SUBTITLE)
        assert(promptInfoSlot.captured.negativeButtonText == AUTH_PROMPT_CANCEL)
    }
}
