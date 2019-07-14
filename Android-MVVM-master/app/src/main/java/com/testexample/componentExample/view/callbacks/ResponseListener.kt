package com.testexample.componentExample.view.callbacks

import com.testexample.componentExample.data.local.entity.ArticleEntity

interface ResponseListener {

    fun onSuccess(data: ArticleEntity)
    fun onFailure(message: String)
}
