package com.ivanatanova.home2eye.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ivanatanova.home2eye.di.Injectable
import com.ivanatanova.home2eye.viewmodels.ViewModelProviderFactory
import javax.inject.Inject

abstract class BaseAuthFragment: Fragment(),
    Injectable
{

    val TAG: String = "AppDebug"

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        cancelActiveJobs()
    }

    private fun cancelActiveJobs(){
        viewModel.cancelActiveJobs()
    }

}



























