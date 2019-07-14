package com.testexample.componentExample.data.remote

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import android.os.AsyncTask
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.MediatorLiveData

import com.google.gson.stream.MalformedJsonException
import com.testexample.componentExample.BaseApplication
import com.testexample.componentExample.R
import com.testexample.componentExample.data.local.entity.ArticleEntity

import java.io.IOException
import java.net.SocketTimeoutException

import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

/**
 * This class act as the decider to cache the response/ fetch from the service always
 */
abstract class NetworkBoundResource<T, V>
@MainThread
protected constructor(){

    private val result = MediatorLiveData<Resource<T>>()

    val asLiveData: LiveData<Resource<T>>
        get() = result

    init {
        result.value = Resource.loading(null)

        // Always load the data from DB intially so that we have
        val dbSource = loadFromDb()

        // Fetch the data from network and add it to the resource
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch()) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    if (null != newData)
                        result.value = Resource.success(newData)
                }
            }
        }
    }

    /**
     * This method fetches the data from remoted service and save it to local db
     * @param dbSource - Database source
     */
    private fun fetchFromNetwork(dbSource: LiveData<T>) {
        result.addSource(dbSource) { newData -> result.setValue(Resource.loading(newData)) }
        createCall().enqueue(object : Callback<V> {
            override fun onResponse(call: Call<V>, response: Response<V>) {
                result.removeSource(dbSource)
                saveResultAndReInit(response.body())
            }

            override fun onFailure(call: Call<V>, t: Throwable) {
                result.removeSource(dbSource)
                result.addSource(dbSource) { newData -> result.setValue(Resource.error(getCustomErrorMessage(t), newData)) }
            }
        })
    }

    private fun getCustomErrorMessage(error: Throwable): String {

        return if (error is SocketTimeoutException) {
            BaseApplication.appContext!!.getString(R.string.requestTimeOutError)
        } else if (error is MalformedJsonException) {
            BaseApplication.appContext!!.getString(R.string.responseMalformedJson)
        } else if (error is IOException) {
            BaseApplication.appContext!!.getString(R.string.networkError)
        } else if (error is HttpException) {
            error.response().message()
        } else {
            BaseApplication.appContext!!.getString(R.string.unknownError)
        }

    }

    @SuppressLint("StaticFieldLeak")
    @MainThread
    private fun saveResultAndReInit(response: V?) {
        object : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {
                saveCallResult(response)
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                result.addSource(loadFromDb()) { newData ->
                    if (null != newData)
                        result.value = Resource.success(newData)
                }
            }
        }.execute()
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: V?)

    @MainThread
    private fun shouldFetch(): Boolean {
        return true
    }

    @MainThread
    protected abstract fun loadFromDb(): LiveData<T>

    @MainThread
    protected abstract fun createCall(): Call<V>
}
