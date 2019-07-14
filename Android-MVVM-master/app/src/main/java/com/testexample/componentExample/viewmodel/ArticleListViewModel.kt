package com.testexample.componentExample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel


import com.testexample.componentExample.data.local.entity.ArticleEntity
import com.testexample.componentExample.data.remote.Resource
import com.testexample.componentExample.data.remote.repository.ArticleRepository

import javax.inject.Inject

/**
 * Article List view model
 */
class ArticleListViewModel @Inject
constructor(articleRepository: ArticleRepository) : ViewModel() {
    val popularArticles: LiveData<Resource<List<ArticleEntity>>> = articleRepository.loadPopularArticles()

}
