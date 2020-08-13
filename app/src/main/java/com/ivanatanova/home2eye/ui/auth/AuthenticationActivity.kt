package com.ivanatanova.home2eye.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ivanatanova.home2eye.BaseActivity
import com.ivanatanova.home2eye.MainActivity
import com.ivanatanova.home2eye.R
import com.ivanatanova.home2eye.di.viewmodel.ViewModelProviderFactory
import com.ivanatanova.home2eye.ui.ResponseType
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject


class AuthenticationActivity : BaseActivity() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel

    val TAG = "Authentication"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        viewModel = ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)
        viewModel.viewState.observe(this, Observer {
            if (it.authToken != null) {
                navigateToMainActivity()
            }
        })

        viewModel.dataState.observe(this, Observer { dataState ->
            dataState.data?.let { data ->
                data.data?.let { event ->
                    event.getContentIfNotHandled()?.let {
                        it.authToken?.let {
                            Log.d(TAG, "AuthActivity, DataState: ${it}")
                            viewModel.setAuthToken(it)
                        }
                    }
                }
                data.response?.let {event ->
                    event.getContentIfNotHandled()?.let{
                        when(it.responseType){
                            is ResponseType.Dialog ->{
                                // show dialog
                            }

                            is ResponseType.Toast ->{
                                showSnackbar(autn_acitvity_layout, it.message!!)
                            }

                            is ResponseType.None ->{
                                // print to log
                                Log.e(TAG, "AuthActivity: Response: ${it.message}, ${it.responseType}" )
                            }
                        }
                    }
                }
            }

            if(dataState.error != null){
                dataState.error?.getContentIfNotHandled()?.let {
                    when(it.response.responseType){
                        is ResponseType.Dialog ->{
                            // show dialog
                        }

                        is ResponseType.Toast ->{
                            showSnackbar(autn_acitvity_layout, it.response.message!!)
                        }

                        is ResponseType.None ->{
                            // print to log
                            Log.e(TAG, "AuthActivity: Response: ${it.response.message}, ${it.response.responseType}" )
                        }
                    }
                }
            }
        })
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
//            .setAction("CLOSE", object : OnClickListener() {
//                fun onClick(view: View?) {}
//            })
            //.setActionTextColor(resources.getColor(R.color.holo_red_light))
            .show()
    }

}