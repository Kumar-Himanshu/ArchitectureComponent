package com.testexample.componentExample.view.callbacks

import com.testexample.componentExample.data.local.entity.ArticleEntity

/**
 * File Description
 *
 *
 */
interface ArticleListCallback {
    fun onArticleClicked(articleEntity: ArticleEntity)
}

