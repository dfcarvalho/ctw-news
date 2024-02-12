package br.com.dcarv.criticalchallenge.authentication.data

import androidx.biometric.BiometricManager
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class AndroidBiometricTest {

    private val biometricManager: BiometricManager = mockk()

    private val androidBiometric = AndroidBiometric(biometricManager)

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

    // TODO: add tests for authentication
//    @Test
//    fun `when authenticating with success, should return true`() = runTest {
//        val activity: FragmentActivity = mockk {
//            coEvery { supportFragmentManager } returns mockk {
//                coEvery { isStateSaved } returns false
//                coEvery { findFragmentByTag(any()) } returns mockk<BiometricFragment>(relaxUnitFun = true)
//            }
//        }
//
//        val result = androidBiometric.authenticate(activity)
//
//        assert(result)
//    }
}
