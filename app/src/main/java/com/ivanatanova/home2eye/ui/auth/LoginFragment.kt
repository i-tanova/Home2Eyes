package com.ivanatanova.home2eye.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.ivanatanova.home2eye.R
import com.ivanatanova.home2eye.model.AuthToken
import com.ivanatanova.home2eye.ui.auth.state.LoginFields
import com.ivanatanova.home2eye.util.ApiEmptyResponse
import com.ivanatanova.home2eye.util.ApiErrorResponse
import com.ivanatanova.home2eye.util.ApiSuccessResponse
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : BaseAuthFragment()  {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        login_button.setOnClickListener{
            viewModel.setAuthToken(AuthToken(2, "231234"))
        }
    }

    fun subscribeObservers(){
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            it.loginFields?.let { loginFields ->
                loginFields.login_email?.let { email ->
                    input_email.setText(email)
                }

                loginFields.login_password?.let{
                    input_password.setText(it)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setLoginFields(LoginFields(input_email.text.toString(), input_password.text.toString()))
    }

}
