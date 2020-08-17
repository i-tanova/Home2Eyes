package com.ivanatanova.home2eye.di.main

import com.ivanatanova.home2eye.api.main.OpenApiMainService
import com.ivanatanova.home2eye.persistence.AccountPropertiesDao
import com.ivanatanova.home2eye.persistence.AppDatabase
import com.ivanatanova.home2eye.persistence.BlogPostDao
import com.ivanatanova.home2eye.repository.main.AccountRepository
import com.ivanatanova.home2eye.repository.main.BlogRepository
import com.ivanatanova.home2eye.repository.main.CreateBlogRepository
import com.ivanatanova.home2eye.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideOpenApiMainService(retrofitBuilder: Retrofit.Builder): OpenApiMainService {
        return retrofitBuilder
            .build()
            .create(OpenApiMainService::class.java)
    }

    @MainScope
    @Provides
    fun provideAccountRepository(
        openApiMainService: OpenApiMainService,
        accountPropertiesDao: AccountPropertiesDao,
        sessionManager: SessionManager
    ): AccountRepository {
        return AccountRepository(openApiMainService, accountPropertiesDao, sessionManager)
    }

    @MainScope
    @Provides
    fun provideBlogPostDao(db: AppDatabase): BlogPostDao {
        return db.getBlogPostDao()
    }

    @MainScope
    @Provides
    fun provideBlogRepository(
        openApiMainService: OpenApiMainService,
        blogPostDao: BlogPostDao,
        sessionManager: SessionManager
    ): BlogRepository {
        return BlogRepository(openApiMainService, blogPostDao, sessionManager)
    }

    @MainScope
    @Provides
    fun provideCreateBlogRepository(
        openApiMainService: OpenApiMainService,
        blogPostDao: BlogPostDao,
        sessionManager: SessionManager
    ): CreateBlogRepository {
        return CreateBlogRepository(openApiMainService, blogPostDao, sessionManager)
    }
}

















