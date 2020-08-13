package com.ivanatanova.home2eye.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivanatanova.home2eye.api.auth.networkresponse.LoginResponse
import com.ivanatanova.home2eye.api.auth.networkresponse.RegistrationResponse
import com.ivanatanova.home2eye.model.AuthToken
import com.ivanatanova.home2eye.repository.auth.AuthRepository
import com.ivanatanova.home2eye.session.SessionManager
import com.ivanatanova.home2eye.ui.BaseViewModel
import com.ivanatanova.home2eye.ui.DataState
import com.ivanatanova.home2eye.ui.auth.state.AuthStateEvent
import com.ivanatanova.home2eye.ui.auth.state.AuthViewState
import com.ivanatanova.home2eye.ui.auth.state.LoginFields
import com.ivanatanova.home2eye.ui.auth.state.RegistrationFields
import com.ivanatanova.home2eye.util.AbsentLiveData
import com.ivanatanova.home2eye.util.GenericApiResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject
constructor(val authRepository: AuthRepository, val sessionManager: SessionManager) : BaseViewModel<AuthStateEvent, AuthViewState>(){

    fun setRegistrationFields(registrationFields: RegistrationFields){
        val update = getCurrentViewStateOrNew()
        if(update.registrationFields == registrationFields){
            return
        }
        update.registrationFields = registrationFields
        _viewState.value = update
    }

    fun setLoginFields(loginFields: LoginFields){
        val update = getCurrentViewStateOrNew()
        if(update.loginFields == loginFields){
            return
        }
        update.loginFields = loginFields
        _viewState.value = update
    }

    fun setAuthToken(authToken: AuthToken){
        val update = getCurrentViewStateOrNew()
        if(update.authToken == authToken){
            return
        }
        viewModelScope.launch {
            sessionManager.login(authToken)
        }
        update.authToken = authToken
        _viewState.value = update
    }

    override fun handleStateEvent(stateEvent: AuthStateEvent): LiveData<DataState<AuthViewState>> {
       when(stateEvent){
           is AuthStateEvent.LoginAttemptEvent -> {
              return authRepository.login(stateEvent.email, stateEvent.password)
           }
           is AuthStateEvent.RegisterAttemptEvent ->{
               return authRepository.register(stateEvent.email, stateEvent.username, stateEvent.password, stateEvent.confirmPassword)
           }
           is AuthStateEvent.CheckPreviousAuthEvent ->{
               return AbsentLiveData.create()
           }
       }
    }

    override fun initNewViewState(): AuthViewState {
        return AuthViewState()
    }
}