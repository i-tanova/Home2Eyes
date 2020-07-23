package com.ivanatanova.home2eye.ui.auth

import androidx.lifecycle.ViewModel
import com.ivanatanova.home2eye.repository.auth.AuthRepository
import javax.inject.Inject

class AuthViewModel @Inject
constructor(val authRepository: AuthRepository) : ViewModel()