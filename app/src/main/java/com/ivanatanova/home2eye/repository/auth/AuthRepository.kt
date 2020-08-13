package com.ivanatanova.home2eye.repository.auth

import android.renderscript.Element
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.ivanatanova.home2eye.api.auth.AuthService
import com.ivanatanova.home2eye.api.auth.networkresponse.LoginResponse
import com.ivanatanova.home2eye.api.auth.networkresponse.RegistrationResponse
import com.ivanatanova.home2eye.model.AuthToken
import com.ivanatanova.home2eye.persistence.AccountPropertiesDao
import com.ivanatanova.home2eye.persistence.AuthTokenDao
import com.ivanatanova.home2eye.session.SessionManager
import com.ivanatanova.home2eye.ui.*
import com.ivanatanova.home2eye.ui.auth.state.AuthViewState
import com.ivanatanova.home2eye.util.ApiEmptyResponse
import com.ivanatanova.home2eye.util.ApiErrorResponse
import com.ivanatanova.home2eye.util.ApiSuccessResponse
import com.ivanatanova.home2eye.util.GenericApiResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    val authTokenDao: AuthTokenDao,
    val accountPropertiesDao: AccountPropertiesDao,
    val authService: AuthService,
    val sessionManager: SessionManager
) {

    fun login(email: String, password: String): LiveData<DataState<AuthViewState>> {
        val result = authService.login(email, password)
        return result.map {response ->
            when(response){
                is ApiSuccessResponse<LoginResponse> -> {
                    DataState.data(AuthViewState(authToken = AuthToken(response.body.pk, response.body.token)))
                }
                is ApiErrorResponse<LoginResponse> -> {
                    DataState.error<AuthViewState>(Response(response.errorMessage, ResponseType.Toast()))
                }
                is ApiEmptyResponse<LoginResponse> -> {
                    DataState.error<AuthViewState>(Response("Error",  ResponseType.Toast()))
                }
            }
        }
    }

    fun register(email: String, username: String, password: String, confirmPassword: String): LiveData<DataState<AuthViewState>> {
        val result = authService.register(email, username, password, confirmPassword)
        return result.map {response ->
            when(response){
                is ApiSuccessResponse<RegistrationResponse> -> {
                    DataState.data(AuthViewState(authToken = AuthToken(response.body.pk, response.body.token)))
                }
                is ApiErrorResponse<RegistrationResponse> -> {
                    DataState.error<AuthViewState>(Response(response.errorMessage, ResponseType.Toast()))
                }
                is ApiEmptyResponse<RegistrationResponse> -> {
                    DataState.error<AuthViewState>(Response("Error",  ResponseType.Toast()))
                }
            }
        }
    }
}