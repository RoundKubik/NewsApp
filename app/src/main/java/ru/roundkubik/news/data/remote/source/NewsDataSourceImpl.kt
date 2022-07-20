package ru.roundkubik.news.data.remote.source

import io.reactivex.Single
import ru.roundkubik.news.core.entity.HeadlinesError
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.data.remote.api.NewsApiService
import ru.roundkubik.news.data.remote.model.toHeadlines
import ru.roundkubik.news.data.source.NewsDataSource
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines
import java.net.HttpURLConnection
import javax.inject.Inject

class NewsDataSourceImpl @Inject constructor(
    private val api: NewsApiService
) : NewsDataSource {

    override fun getHeadlines(category: Category): Single<NewsResult<Headlines>> {
        return api.getTopHeadlines("us", category.name, "88ecaeed8499427db378de30423546c3").map { response ->
            when {
                response.isSuccessful -> {
                    val res = response.body()
                    if (res != null) {
                        NewsResult.Success(res.toHeadlines(category))
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
}