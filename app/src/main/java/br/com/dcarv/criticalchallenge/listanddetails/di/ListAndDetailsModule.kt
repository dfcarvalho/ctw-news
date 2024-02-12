package br.com.dcarv.criticalchallenge.listanddetails.di

import br.com.dcarv.criticalchallenge.headlinedetails.di.HeadlineDetailsModule
import br.com.dcarv.criticalchallenge.listanddetails.presentation.ListAndDetailsReducer
import br.com.dcarv.criticalchallenge.listanddetails.presentation.ListAndDetailsUdaChain
import br.com.dcarv.criticalchallenge.listanddetails.presentation.ListAndDetailsViewState
import br.com.dcarv.criticalchallenge.sourcelist.di.SourceListModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module(includes = [SourceListModule::class, HeadlineDetailsModule::class])
@InstallIn(ViewModelComponent::class)
class ListAndDetailsModule {

    @Provides
    fun provideUdaChain(reducer: ListAndDetailsReducer): ListAndDetailsUdaChain = ListAndDetailsUdaChain(
        reducer = reducer,
        initialViewState = ListAndDetailsViewState(),
    )
}
