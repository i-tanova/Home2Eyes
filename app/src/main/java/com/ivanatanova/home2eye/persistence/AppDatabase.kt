package com.ivanatanova.home2eye.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ivanatanova.home2eye.models.AccountProperties
import com.ivanatanova.home2eye.models.AuthToken
import com.ivanatanova.home2eye.models.BlogPost

@Database(entities = [AuthToken::class, AccountProperties::class, BlogPost::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getAuthTokenDao(): AuthTokenDao

    abstract fun getAccountPropertiesDao(): AccountPropertiesDao

    abstract fun getBlogPostDao(): BlogPostDao

    companion object{
        val DATABASE_NAME: String = "app_db"
    }


}








