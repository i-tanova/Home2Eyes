package com.ivanatanova.home2eye.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.ivanatanova.home2eye.R
import com.ivanatanova.home2eye.ui.auth.state.AuthStateEvent
import com.ivanatanova.home2eye.ui.auth.state.LoginFields
import com.ivanatanova.home2eye.ui.auth.state.RegistrationFields
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.input_email
import kotlinx.android.synthetic.main.fragment_login.input_password
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : BaseAuthFragment()  {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }

    fun subscribeObservers(){
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            it.registrationFields?.let { registrationFields ->
                registrationFields.registrationEmail?.let { email ->
                    input_email.setText(email)
                }

                registrationFields.registrationUsername?.let {
                    input_username.setText(it)
                }

                registrationFields.registrationPassword?.let{
                    input_password.setText(it)
                }

                registrationFields.registrationConfirmPassword?.let{
                    input_password_confirm.setText(it)
                }
            }
        })

        register_button.setOnClickListener{
            register()
        }
    }

    fun register(){
        viewModel.setStateEvent(AuthStateEvent.RegisterAttemptEvent(input_email.text.toString(),
            input_username.text.toString(), input_password.text.toString(), input_password_confirm.text.toString()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setRegistrationFields(RegistrationFields(registrationEmail=input_email.text.toString(),
            registrationUsername = input_username.text.toString(), registrationPassword = input_password.text.toString(),
            registrationConfirmPassword = input_password_confirm.text.toString()))
    }




}
