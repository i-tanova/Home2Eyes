package com.ivanatanova.home2eye

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.ivanatanova.home2eye.session.SessionManager
import com.ivanatanova.home2eye.ui.auth.AuthenticationActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager.authTokenLiveData.observe(this, Observer {
            if (it == null) {
                navigateToLogin()
            }
        })

        toolbar.setOnClickListener {
            val scope = MainScope()
            scope.launch {
                logout()
            }
        }
    }

    private suspend fun logout() {
        sessionManager.logout(2)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, AuthenticationActivity::class.java)
        startActivity(intent)
        finish()
    }
}
