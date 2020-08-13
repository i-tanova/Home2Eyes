package com.ivanatanova.home2eye.di

import android.app.Application
import com.ivanatanova.home2eye.MainActivity
import com.ivanatanova.home2eye.di.auth.AuthFragmentBuildersModule
import com.ivanatanova.home2eye.di.auth.AuthModule
import com.ivanatanova.home2eye.di.auth.AuthScope
import com.ivanatanova.home2eye.di.auth.AuthViewModelModule
import com.ivanatanova.home2eye.persistence.AuthTokenDao
import com.ivanatanova.home2eye.session.SessionManager
import com.ivanatanova.home2eye.ui.auth.AuthenticationActivity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuildersModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthenticationActivity

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}