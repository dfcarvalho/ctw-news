package br.com.dcarv.criticalchallenge.sourcelist.di

import br.com.dcarv.criticalchallenge.sourcelist.presentation.SourceListReducer
import br.com.dcarv.criticalchallenge.sourcelist.presentation.SourceListUdaChain
import br.com.dcarv.criticalchallenge.sourcelist.presentation.SourceListViewState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class SourceListModule {

    @Provides
    fun provideUdaChain(reducer: SourceListReducer): SourceListUdaChain = SourceListUdaChain(
        reducer = reducer,
        initialViewState = SourceListViewState(),
    )
}
