package br.com.dcarv.criticalchallenge.authentication.data

import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import br.com.dcarv.criticalchallenge.R
import br.com.dcarv.criticalchallenge.common.presentation.StringResourceProvider
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AndroidBiometric @Inject constructor(
    private val biometricManager: BiometricManager,
    private val biometricPromptFactory: BiometricPromptFactory,
    private val stringResourceProvider: StringResourceProvider,
) {

    fun canAuthenticateWithFingerprint(): Boolean {
        return when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }

    suspend fun authenticate(activity: FragmentActivity): Boolean = suspendCoroutine { continuation ->
        val callback = createCallbackWithContinuation(
            onSuccess =  { continuation.resume(true) },
            onFailure =  { continuation.resume(false) },
        )

        val biometricPrompt = biometricPromptFactory.create(activity, callback)

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(stringResourceProvider.get(R.string.auth_prompt_title))
            .setSubtitle(stringResourceProvider.get(R.string.auth_prompt_subtitle))
            .setNegativeButtonText(stringResourceProvider.get(R.string.auth_prompt_cancel))
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun createCallbackWithContinuation(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            onFailure()
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            onSuccess()
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Log.d("Critical - Authentication", "Attempted to authenticate with biometric but failed")
        }
    }
}
