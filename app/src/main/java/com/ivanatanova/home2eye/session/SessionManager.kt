package com.ivanatanova.home2eye.session

import android.app.Application
import com.ivanatanova.home2eye.persistence.AuthTokenDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject
constructor(val authToken: AuthTokenDao, val application: Application) {
}