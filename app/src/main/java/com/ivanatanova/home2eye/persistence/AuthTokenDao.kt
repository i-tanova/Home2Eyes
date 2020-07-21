package com.ivanatanova.home2eye.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ivanatanova.home2eye.model.AuthToken

@Dao
interface AuthTokenDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(token: AuthToken): Long

    // Logout
    @Query("UPDATE auth_token SET token=null WHERE account_pk =:pk")
    fun nullifyToken(pk: Int): Int
}