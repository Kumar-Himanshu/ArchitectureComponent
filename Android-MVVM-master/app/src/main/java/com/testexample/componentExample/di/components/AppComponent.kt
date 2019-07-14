package com.testexample.componentExample.di.components

import android.app.Application

import com.testexample.componentExample.BaseApplication
import com.testexample.componentExample.di.builder.ActivityBuilderModule
import com.testexample.componentExample.di.module.AppModule

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * The main application component which initializes all the dependent modules
 */
@Singleton
@Component(modules = [AppModule::class, AndroidSupportInjectionModule::class, ActivityBuilderModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(bsaeApp: BaseApplication)
}