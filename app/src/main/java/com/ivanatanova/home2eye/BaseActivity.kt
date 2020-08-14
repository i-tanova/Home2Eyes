package com.ivanatanova.home2eye

import com.ivanatanova.home2eye.session.SessionManager
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity: DaggerAppCompatActivity(){

    val TAG: String = "AppDebug"

    @Inject
    lateinit var sessionManager: SessionManager

}