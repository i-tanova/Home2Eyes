package com.ivanatanova.home2eye.di

import androidx.lifecycle.ViewModelProvider
import com.ivanatanova.home2eye.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

}