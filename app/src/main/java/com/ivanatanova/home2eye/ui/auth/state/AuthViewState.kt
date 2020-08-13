package com.ivanatanova.home2eye.ui.auth.state

import com.ivanatanova.home2eye.model.AuthToken


data class AuthViewState(
    var registrationFields: RegistrationFields? = RegistrationFields(),
    var loginFields: LoginFields? = LoginFields(),
    var authToken: AuthToken? = null
)
data class RegistrationFields(var registrationEmail: String? = null,
                              var registrationUsername: String? = null,
                              var registrationPassword: String? = null,
                              val registrationConfirmPassword: String? = null){
    class RegistrationError{

        companion object{
            fun mustFillAllFields() :String{
                return "All fields are required"
            }
            fun passwordDoNotMatch() :String{
                return "Passwords must match"
            }

            fun none(): String {
                return "None"
            }
        }
    }

    fun isValidForRegistration(): String{
        if(registrationEmail.isNullOrBlank() || registrationUsername.isNullOrBlank()
            || registrationPassword.isNullOrBlank() || registrationConfirmPassword.isNullOrBlank()){

            return RegistrationError.mustFillAllFields()
        }

        if((registrationPassword?.contentEquals(registrationConfirmPassword)) == false){
            return RegistrationError.passwordDoNotMatch()
        }

        return RegistrationError.none()
    }
}


data class LoginFields(
    var login_email: String? = null,
    var login_password: String? = null
){
    class LoginError {

        companion object{

            fun mustFillAllFields(): String{
                return "You can't login without an email and password."
            }

            fun none():String{
                return "None"
            }

        }
    }

    fun isValidForLogin(): String{

        if(login_email.isNullOrEmpty()
            || login_password.isNullOrEmpty()){

            return LoginError.mustFillAllFields()
        }
        return LoginError.none()
    }

    override fun toString(): String {
        return "LoginState(email=$login_email, password=$login_password)"
    }
}