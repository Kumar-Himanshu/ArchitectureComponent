package com.testexample.componentExample.data.remote

import com.testexample.componentExample.data.remote.model.PopularArticleResponse

import retrofit2.Call
import retrofit2.http.GET

/**
 * The APIService interface which will contain the semantics of all the REST calls.
 */
interface ApiService {

    @GET("/2.2/search?order=desc&sort=activity&intitle=perl&site=stackoverflow")
    fun loadPopularArticles(): Call<PopularArticleResponse>
}
