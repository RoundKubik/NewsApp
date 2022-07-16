package ru.roundkubik.news.data.repository

import io.reactivex.Single
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.data.source.NewsDataSource
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines
import ru.roundkubik.news.domain.repository.NewsRepository

class NewsRepositoryImpl(private val dataSource: NewsDataSource) : NewsRepository {
    override fun getHeadlines(category: Category): Single<NewsResult<Headlines>> {
        return dataSource.getHeadlines(category)
    }
}