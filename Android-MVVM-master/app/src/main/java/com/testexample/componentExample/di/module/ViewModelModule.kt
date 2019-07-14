package com.testexample.componentExample.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.testexample.componentExample.viewmodel.ArticleListViewModel
import com.testexample.componentExample.viewmodel.ViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Allows us to inject dependencies via constructor injection
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ArticleListViewModel::class)
    internal abstract fun bindsArticleListViewModel(articleListViewModel: ArticleListViewModel): ViewModel


    @Binds
    internal abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
