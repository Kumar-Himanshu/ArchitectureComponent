package com.testexample.componentExample.data.remote.model

import com.google.gson.annotations.SerializedName
import com.testexample.componentExample.data.local.entity.ArticleEntity

/**
 * The model class which holds the top popular articles data
 *
 */
class PopularArticleResponse {

    /**
     * This method return the list of article entities
     * @return List of entities
     */
    /**
     * This method sets the article entities
     * @param popularArticles - articleslist
     */
    @SerializedName("items")
    var popularArticles: List<ArticleEntity>? = null
}
