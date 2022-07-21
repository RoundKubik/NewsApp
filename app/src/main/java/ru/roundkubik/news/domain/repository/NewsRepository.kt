package ru.roundkubik.news.domain.repository

import io.reactivex.Single
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines

interface NewsRepository {
    fun getHeadlinesArticlesSortedByDescending(category: Category): Single<NewsResult<Headlines>>

    fun getCachedHeadlines(category: Category): Single<NewsResult<Headlines>>

    fun addArticlesInDb(headlines: Headlines)

    fun deleteArticlesFromDb(headlines: Headlines)
}