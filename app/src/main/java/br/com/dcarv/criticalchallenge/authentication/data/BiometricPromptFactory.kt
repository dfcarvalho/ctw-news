package br.com.dcarv.criticalchallenge.authentication.data

import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import javax.inject.Inject

class BiometricPromptFactory @Inject constructor() {

    fun create(
        activity: FragmentActivity,
        callback: BiometricPrompt.AuthenticationCallback,
    ): BiometricPrompt {

        return BiometricPrompt(
            activity,
            ContextCompat.getMainExecutor(activity),
            callback,
        )
    }
}
