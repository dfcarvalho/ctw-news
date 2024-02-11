package br.com.dcarv.criticalchallenge.headlinedetails.di

import br.com.dcarv.criticalchallenge.headlinedetails.presentation.HeadlineDetailsReducer
import br.com.dcarv.criticalchallenge.headlinedetails.presentation.HeadlineDetailsUdaChain
import br.com.dcarv.criticalchallenge.headlinedetails.presentation.HeadlineDetailsViewState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HeadlineDetailsModule {

    @Provides
    fun provideUdaChain(reducer: HeadlineDetailsReducer): HeadlineDetailsUdaChain = HeadlineDetailsUdaChain(
        reducer = reducer,
        initialViewState = HeadlineDetailsViewState(),
    )
}
