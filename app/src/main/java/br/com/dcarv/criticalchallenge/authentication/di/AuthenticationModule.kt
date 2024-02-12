package br.com.dcarv.criticalchallenge.authentication.di

import android.content.Context
import androidx.biometric.BiometricManager
import br.com.dcarv.criticalchallenge.authentication.presentation.AuthenticationReducer
import br.com.dcarv.criticalchallenge.authentication.presentation.AuthenticationUdaChain
import br.com.dcarv.criticalchallenge.authentication.presentation.AuthenticationViewState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class AuthenticationModule {

    @Provides
    fun provideAuthenticationUdaChain(reducer: AuthenticationReducer): AuthenticationUdaChain = AuthenticationUdaChain(
        reducer = reducer,
        initialViewState = AuthenticationViewState(),
    )

    @Provides
    fun provideBiometricManager(context: Context): BiometricManager = BiometricManager.from(context)
}
