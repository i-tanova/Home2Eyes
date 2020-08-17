package com.ivanatanova.home2eye.di.auth

import androidx.lifecycle.ViewModel
import com.ivanatanova.home2eye.di.ViewModelKey
import com.ivanatanova.home2eye.ui.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

}