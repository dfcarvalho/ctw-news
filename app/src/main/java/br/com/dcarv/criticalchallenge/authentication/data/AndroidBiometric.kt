package br.com.dcarv.criticalchallenge.authentication.data

import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AndroidBiometric @Inject constructor(
    private val biometricManager: BiometricManager,
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

        val biometricPrompt = BiometricPrompt(
            activity,
            ContextCompat.getMainExecutor(activity),
            callback,
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric authentication")
            .setSubtitle("Authenticate using your biometric credential")
            .setNegativeButtonText("Cancel")
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
