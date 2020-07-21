package com.ivanatanova.home2eye.session

import android.app.Application
import com.ivanatanova.home2eye.persistence.AuthTokenDao

class SessionManager(val authToken: AuthTokenDao, val application: Application) {
}