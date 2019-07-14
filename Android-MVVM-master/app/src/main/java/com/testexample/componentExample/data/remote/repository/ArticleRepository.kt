package com.testexample.componentExample.data.remote.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData

import com.testexample.componentExample.data.local.dao.ArticleDao
import com.testexample.componentExample.data.local.entity.ArticleEntity
import com.testexample.componentExample.data.remote.ApiService
import com.testexample.componentExample.data.remote.NetworkBoundResource
import com.testexample.componentExample.data.remote.Resource
import com.testexample.componentExample.data.remote.model.PopularArticleResponse
import com.testexample.componentExample.view.callbacks.ResponseListener


import javax.inject.Inject

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call

/**
 * The article repository which has access to local and remote data fetching services
 *
 */
class ArticleRepository
@Inject
internal constructor(private val articleDao: ArticleDao, private val apiService: ApiService){
    /**
     * This method fetches the popular articles from the service.
     * Once the fetching is done the data is cached to local db so that the app can even work offline
     * @param howfarback index indicating how far back
     * @return List of articles
     */
    fun loadPopularArticles(): LiveData<Resource<List<ArticleEntity>>> {
        return object : NetworkBoundResource<List<ArticleEntity>, PopularArticleResponse>() {

            override fun saveCallResult(item: PopularArticleResponse?) {
                if (null != item)
                    articleDao.saveArticles(item.popularArticles!!)
            }

            override fun loadFromDb(): LiveData<List<ArticleEntity>> {
                return articleDao.loadPopularArticles()
            }

            override fun createCall(): Call<PopularArticleResponse> {
                return apiService.loadPopularArticles()
            }
        }.asLiveData
    }


    /**
     * This method fetches the HTML comntent from the url and parses it and fills the model
     * @param url url to be fetched
     * @param responseListener callback
     */
    @SuppressLint("CheckResult")
    fun loadArticleDetails(url: String, responseListener: ResponseListener) {
        val articleDetails = ArticleEntity()
        Observable.fromCallable {
//            val document = Jsoup.connect(url).get()
//            articleDetails.title = document.title()
//            articleDetails.body = document.select("p").text()
            false
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> responseListener.onSuccess(articleDetails) },
                        { error -> responseListener.onFailure(error.message!!) })

    }

}
