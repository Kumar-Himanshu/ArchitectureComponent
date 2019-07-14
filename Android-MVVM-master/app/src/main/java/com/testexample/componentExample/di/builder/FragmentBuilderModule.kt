package com.testexample.componentExample.di.builder

import com.testexample.componentExample.view.fragment.ArticleListFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * This builder provides android injector service to fragments
 */
@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    internal abstract fun contributeArticleListFragment(): ArticleListFragment

}
