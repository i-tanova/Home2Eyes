package com.ivanatanova.home2eye.session

import android.app.Application
import android.os.Build.VERSION_CODES.M
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ivanatanova.home2eye.model.AuthToken
import com.ivanatanova.home2eye.persistence.AuthTokenDao
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject
constructor(val authTokenDao: AuthTokenDao, val application: Application) {

    val _authToken = MutableLiveData<AuthToken>()
    val authTokenLiveData: LiveData<AuthToken> = _authToken

    suspend fun login(authToken: AuthToken){
        if(authTokenLiveData.value == authToken){
            return
        }else {
            coroutineScope{
                launch(Dispatchers.IO) {
                    authTokenDao.insert(authToken)

                    withContext(Dispatchers.Main) {
                        _authToken.value = authToken
                    }
                }
            }
        }
    }

    suspend fun logout(pk:Int){
        coroutineScope {
            launch(Dispatchers.IO) {
                authTokenDao.nullifyToken(pk)

                withContext(Dispatchers.Main){
                    _authToken.value = null
                }

            }
        }
    }
}