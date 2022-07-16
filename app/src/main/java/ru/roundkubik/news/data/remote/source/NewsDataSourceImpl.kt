package ru.roundkubik.news.data.remote.source

import ru.roundkubik.news.core.entity.HeadlinesError
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.data.remote.api.NewsApiService
import ru.roundkubik.news.data.remote.model.toHeadlines
import ru.roundkubik.news.data.source.NewsDataSource
import ru.roundkubik.news.domain.model.Headlines
import java.net.HttpURLConnection

class NewsDataSourceImpl(
    private val api: NewsApiService
) : NewsDataSource {

    override fun getHeadlines(): NewsResult<Headlines> {
        val response = api.getTopHeadlines()
        return when {
            response.isSuccessful -> {
                val res = response.body()
                if (res != null) {
                    NewsResult.Success(res.toHeadlines())
                } else {
                    NewsResult.Error(HeadlinesError.Unknown)
                }
            }
            response.code() == HttpURLConnection.HTTP_NOT_FOUND -> {
                NewsResult.Error(HeadlinesError.HeadlinesNotFound)
            }
            else -> {
                NewsResult.Error(HeadlinesError.Unknown)
            }
        }
    }
}