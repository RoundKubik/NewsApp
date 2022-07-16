package ru.roundkubik.news.data.source

import io.reactivex.Single
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines

interface NewsDataSource {
    fun getHeadlines(category: Category): Single<NewsResult<Headlines>>
}