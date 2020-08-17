package com.ivanatanova.home2eye.di.main

import com.ivanatanova.home2eye.ui.main.account.AccountFragment
import com.ivanatanova.home2eye.ui.main.account.ChangePasswordFragment
import com.ivanatanova.home2eye.ui.main.account.UpdateAccountFragment
import com.ivanatanova.home2eye.ui.main.blog.BlogFragment
import com.ivanatanova.home2eye.ui.main.blog.UpdateBlogFragment
import com.ivanatanova.home2eye.ui.main.blog.ViewBlogFragment
import com.ivanatanova.home2eye.ui.main.create_blog.CreateBlogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeBlogFragment(): BlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeAccountFragment(): AccountFragment

    @ContributesAndroidInjector()
    abstract fun contributeChangePasswordFragment(): ChangePasswordFragment

    @ContributesAndroidInjector()
    abstract fun contributeCreateBlogFragment(): CreateBlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeUpdateBlogFragment(): UpdateBlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeViewBlogFragment(): ViewBlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeUpdateAccountFragment(): UpdateAccountFragment
}