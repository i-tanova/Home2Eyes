package com.ivanatanova.home2eye.di.auth

import com.ivanatanova.home2eye.ui.auth.ForgotPasswordFragment
import com.ivanatanova.home2eye.ui.auth.LauncherFragment
import com.ivanatanova.home2eye.ui.auth.LoginFragment
import com.ivanatanova.home2eye.ui.auth.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeLauncherFragment(): LauncherFragment

    @ContributesAndroidInjector()
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector()
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector()
    abstract fun contributeForgotPasswordFragment(): ForgotPasswordFragment



}