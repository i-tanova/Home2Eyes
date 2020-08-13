package com.ivanatanova.home2eye.di.auth

import android.app.Application
import com.ivanatanova.home2eye.api.auth.AuthService
import com.ivanatanova.home2eye.persistence.AccountPropertiesDao
import com.ivanatanova.home2eye.persistence.AuthTokenDao
import com.ivanatanova.home2eye.repository.auth.AuthRepository
import com.ivanatanova.home2eye.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AuthModule {

    @AuthScope
    @Provides
    fun provideAuthApiService(retrofit: Retrofit.Builder): AuthService {
        return retrofit.build()
            .create(AuthService::class.java)
    }

    @AuthScope
    @Provides
    fun provideAuthRepository(
        sessionManager: SessionManager,
        authTokenDao: AuthTokenDao,
        accountPropertiesDao: AccountPropertiesDao,
        openApiAuthService: AuthService
    ): AuthRepository {
        return AuthRepository(
            authTokenDao,
            accountPropertiesDao,
            openApiAuthService,
            sessionManager
        )
    }

//    @Provides
//    fun provideSessionManager(authTokenDao: AuthTokenDao, application: Application): SessionManager{
//        return SessionManager(authTokenDao, application)
//    }

}