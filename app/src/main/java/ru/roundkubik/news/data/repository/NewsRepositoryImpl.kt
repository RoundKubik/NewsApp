package ru.roundkubik.news.data.repository

import io.reactivex.Single
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.data.source.ArticleCacheDataSource
import ru.roundkubik.news.data.source.NewsDataSource
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines
import ru.roundkubik.news.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val maxPageSize: Int,
    private val remoteDataSource: NewsDataSource,
    private val cacheDataSource: ArticleCacheDataSource
) : NewsRepository {

    override fun getHeadlinesArticlesSortedByDescending(category: Category): Single<NewsResult<Headlines>> {
        return remoteDataSource.getHeadlinesArticlesSortedByDescending(category)
    }

    override fun getCachedHeadlines(
        category: Category
    ): Single<NewsResult<Headlines>> {
        return cacheDataSource.fetchArticles(category, maxPageSize)
    }

    override fun addArticlesInDb(headlines: Headlines) {
        cacheDataSource.addArticles(headlines)
    }

    override fun deleteArticlesFromDb(headlines: Headlines) {
        cacheDataSource.deleteArticles(headlines)
    }


}