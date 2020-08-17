package com.ivanatanova.home2eye.api.auth

import androidx.lifecycle.LiveData
import com.ivanatanova.home2eye.api.auth.network_responses.LoginResponse
import com.ivanatanova.home2eye.api.auth.network_responses.RegistrationResponse
import com.ivanatanova.home2eye.util.GenericApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService{

    @POST("account/login")
    @FormUrlEncoded
    fun login(@Field(value="username") email: String, @Field(value="password") password: String): LiveData<GenericApiResponse<LoginResponse>>

    @POST("account/register")
    @FormUrlEncoded
    fun register(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("password2") password2: String
    ): LiveData<GenericApiResponse<RegistrationResponse>>


}