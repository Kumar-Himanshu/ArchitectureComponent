package com.testexample.componentExample.di.builder

import com.testexample.componentExample.view.activity.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * The module which provides the android injection service to Activities.
 */
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    internal abstract fun mainActivity(): MainActivity


}
