package com.ivanatanova.home2eye.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ivanatanova.home2eye.model.AccountProperties
import com.ivanatanova.home2eye.model.AuthToken

@Database(entities = [AuthToken::class, AccountProperties::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getAuthTokenDao(): AuthTokenDao

    abstract fun getAccountPropertiesDao(): AccountPropertiesDao

    companion object{
        const val DATABASE_NAME = "app_db"
    }
}