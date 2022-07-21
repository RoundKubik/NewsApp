package ru.roundkubik.news.data.source

import io.reactivex.Single
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.data.cache.dao.ArticlesDao
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines

interface ArticleCacheDataSource {

    fun articlesDao(): ArticlesDao

    fun fetchArticles(category: Category, maxPageSize: Int): Single<NewsResult<Headlines>>

    fun addArticles(headlines: Headlines):  Single<NewsResult<List<Long>>>


}