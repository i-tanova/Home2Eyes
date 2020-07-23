package com.ivanatanova.home2eye.di

import com.ivanatanova.home2eye.di.auth.AuthFragmentBuildersModule
import com.ivanatanova.home2eye.di.auth.AuthModule
import com.ivanatanova.home2eye.di.auth.AuthScope
import com.ivanatanova.home2eye.di.auth.AuthViewModelModule
import com.ivanatanova.home2eye.ui.auth.AuthenticationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuildersModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthenticationActivity
}