package com.testexample.componentExample.di.module

import android.app.Application
import androidx.room.Room
import com.google.gson.GsonBuilder

import com.testexample.componentExample.data.local.ArticleDatabase
import com.testexample.componentExample.data.local.dao.ArticleDao
import com.testexample.componentExample.data.remote.ApiConstants
import com.testexample.componentExample.data.remote.ApiService
import com.testexample.componentExample.data.remote.RequestInterceptor
import com.testexample.componentExample.utils.DateTypeAdapter

import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


/**
 * The application module which provides app wide instances of various components
 */
@Module(includes = [ViewModelModule::class])
class AppModule {
    var gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, DateTypeAdapter())
            .create()
    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(ApiConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.readTimeout(ApiConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.writeTimeout(ApiConstants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.addInterceptor(RequestInterceptor())
        okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()

        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideArticleDatabase(application: Application): ArticleDatabase {
        return Room.databaseBuilder(application, ArticleDatabase::class.java, "articles.db").build()
    }

    @Provides
    @Singleton
    internal fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.articleDao()
    }

}
