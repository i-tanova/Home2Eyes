package com.ivanatanova.home2eye.repository.auth

import com.ivanatanova.home2eye.api.auth.AuthService
import com.ivanatanova.home2eye.persistence.AccountPropertiesDao
import com.ivanatanova.home2eye.persistence.AuthTokenDao
import com.ivanatanova.home2eye.session.SessionManager
import javax.inject.Inject

class   AuthRepository @Inject constructor(
    val authTokenDao: AuthTokenDao,
    val accountPropertiesDao: AccountPropertiesDao,
    val authService: AuthService,
    val sessionManager: SessionManager
)