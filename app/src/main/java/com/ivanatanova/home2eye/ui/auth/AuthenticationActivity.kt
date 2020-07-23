package com.ivanatanova.home2eye.ui.auth

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ivanatanova.home2eye.BaseActivity
import com.ivanatanova.home2eye.R
import com.ivanatanova.home2eye.di.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class AuthenticationActivity : BaseActivity(){

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        viewModel = ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)
    }

}