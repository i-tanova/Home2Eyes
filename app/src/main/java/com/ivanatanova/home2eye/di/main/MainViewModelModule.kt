package com.ivanatanova.home2eye.di.main

import androidx.lifecycle.ViewModel
import com.ivanatanova.home2eye.di.ViewModelKey
import com.ivanatanova.home2eye.ui.main.account.AccountViewModel
import com.ivanatanova.home2eye.ui.main.blog.viewmodel.BlogViewModel
import com.ivanatanova.home2eye.ui.main.create_blog.CreateBlogViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(accoutViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BlogViewModel::class)
    abstract fun bindBlogViewModel(blogViewModel: BlogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateBlogViewModel::class)
    abstract fun bindCreateBlogViewModel(createBlogViewModel: CreateBlogViewModel): ViewModel
}








