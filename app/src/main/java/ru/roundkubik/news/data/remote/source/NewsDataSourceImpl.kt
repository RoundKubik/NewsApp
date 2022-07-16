package ru.roundkubik.news.data.remote.source

import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.data.remote.api.NewsApiService
import ru.roundkubik.news.data.source.NewsDataSource
import ru.roundkubik.news.domain.model.Headlines

class NewsDataSourceImpl(
    private val api: NewsApiService
) : NewsDataSource {

    override fun getHeadlines(): NewsResult<Headlines> {
        TODO("Not yet implemented")
    }
}