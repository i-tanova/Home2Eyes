package com.ivanatanova.home2eye.di

import com.ivanatanova.home2eye.di.auth.AuthFragmentBuildersModule
import com.ivanatanova.home2eye.di.auth.AuthModule
import com.ivanatanova.home2eye.di.auth.AuthScope
import com.ivanatanova.home2eye.di.auth.AuthViewModelModule
import com.ivanatanova.home2eye.di.main.MainFragmentBuildersModule
import com.ivanatanova.home2eye.di.main.MainModule
import com.ivanatanova.home2eye.di.main.MainScope
import com.ivanatanova.home2eye.di.main.MainViewModelModule
import com.ivanatanova.home2eye.ui.auth.AuthActivity
import com.ivanatanova.home2eye.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuildersModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

    @MainScope
    @ContributesAndroidInjector(
        modules = [MainModule::class, MainFragmentBuildersModule::class, MainViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity
}