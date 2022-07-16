package ru.roundkubik.news.domain.repository

import io.reactivex.Single
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines

interface NewsRepository {
    fun getHeadlines(category: Category): Single<NewsResult<Headlines>>
}