package com.ivanatanova.home2eye.repository.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.ivanatanova.home2eye.api.auth.AuthService
import com.ivanatanova.home2eye.api.auth.networkresponse.LoginResponse
import com.ivanatanova.home2eye.model.AuthToken
import com.ivanatanova.home2eye.persistence.AccountPropertiesDao
import com.ivanatanova.home2eye.persistence.AuthTokenDao
import com.ivanatanova.home2eye.session.SessionManager
import com.ivanatanova.home2eye.ui.DataState
import com.ivanatanova.home2eye.ui.Response
import com.ivanatanova.home2eye.ui.ResponseType
import com.ivanatanova.home2eye.ui.auth.state.AuthViewState
import com.ivanatanova.home2eye.util.ApiEmptyResponse
import com.ivanatanova.home2eye.util.ApiErrorResponse
import com.ivanatanova.home2eye.util.ApiSuccessResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    val authTokenDao: AuthTokenDao,
    val accountPropertiesDao: AccountPropertiesDao,
    val authService: AuthService,
    val sessionManager: SessionManager
) {

    fun login(email: String, password: String): LiveData<DataState<AuthViewState>> {
        val result = authService.login(email, password)
        return result.map { response ->
            when (response) {
                is ApiSuccessResponse<LoginResponse> -> {
                    DataState.data(
                        AuthViewState(
                            authToken = AuthToken(
                                response.body.pk,
                                response.body.token
                            )
                        )
                    )
                }
                is ApiErrorResponse<LoginResponse> -> {
                    DataState.error<AuthViewState>(
                        Response(
                            response.errorMessage,
                            ResponseType.Toast()
                        )
                    )
                }
                is ApiEmptyResponse<LoginResponse> -> {
                    DataState.error<AuthViewState>(Response("Error", ResponseType.Toast()))
                }
            }
        }
    }

    fun register(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): LiveData<DataState<AuthViewState>> {
        return authService.register(email, username, password, confirmPassword)
            .switchMap { response ->
                object : LiveData<DataState<AuthViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when (response) {
                            is ApiSuccessResponse -> {
                                if(response.body.errorMessage != null){
                                    value = DataState.error(
                                        Response(
                                            message = response.body.errorMessage,
                                            responseType = ResponseType.Toast()
                                        )
                                    )
                                }else {
                                    value = DataState.data(
                                        AuthViewState(
                                            authToken = AuthToken(
                                                response.body.pk,
                                                response.body.token
                                            )
                                        ),
                                        response = null
                                    )
                                }
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    Response(
                                        message = response.errorMessage,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    Response(
                                        message = "Unknown",
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                        }
                    }
                }
            }
//                is ApiSuccessResponse<RegistrationResponse> -> {
//                    if(response.body.errorMessage == null) {
//                        DataState.data(
//                            AuthViewState(
//                                authToken = AuthToken(
//                                    response.body.pk,
//                                    response.body.token
//                                )
//                            ))
//                    }else{
//                                    DataState.error(
//                                   Response(response.body.errorMessage, ResponseType.Toast()))
//                    }
//                }
//                is ApiErrorResponse<RegistrationResponse> -> {
//                    DataState.error<AuthViewState>(Response(response.errorMessage, ResponseType.Toast()))
//                }
//                is ApiEmptyResponse<RegistrationResponse> -> {
//                    DataState.error<AuthViewState>(Response("Error",  ResponseType.Toast()))
//                }
    }


}