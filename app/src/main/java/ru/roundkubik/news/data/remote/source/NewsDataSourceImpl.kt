package ru.roundkubik.news.data.remote.source

import io.reactivex.Single
import ru.roundkubik.news.R
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.core.resource_provider.ResourceProvider
import ru.roundkubik.news.data.remote.api.NewsApiService
import ru.roundkubik.news.data.remote.model.HeadlinesError
import ru.roundkubik.news.data.remote.model.toHeadlinesArticlesSortedByDescending
import ru.roundkubik.news.data.source.NewsDataSource
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines
import java.net.HttpURLConnection
import javax.inject.Inject

class NewsDataSourceImpl @Inject constructor(
    private val api: NewsApiService,
    private val resourceProvider: ResourceProvider
) : NewsDataSource {

    override fun getHeadlinesArticlesSortedByDescending(category: Category): Single<NewsResult<Headlines>> {
        return api.getTopHeadlines(
            resourceProvider.string(R.string.supported_news_country),
            category.identity,
            resourceProvider.string(R.string.news_api_key)
        ).map { response ->
            when {
                response.isSuccessful -> {
                    val res = response.body()
                    if (res == null) {
                        NewsResult.Error(
                            HeadlinesError(
                                resourceProvider.string(R.string.error_headlines_unknown)
                            )
                        )
                    } else {
                        if (res.articles.isEmpty()) {
                            NewsResult.Error(
                                HeadlinesError(
                                    resourceProvider.string(
                                        R.string.error_headlines_empty_headlines,
                                        category.name
                                    )
                                )
                            )
                        } else {
                            NewsResult.Success(res.toHeadlinesArticlesSortedByDescending(category))
                        }
                    }
                }
                response.code() == HttpURLConnection.HTTP_NOT_FOUND -> {
                    NewsResult.Error(
                        HeadlinesError(
                            resourceProvider.string(
                                R.string.error_headlines_cannot_find_requested,
                                category.name
                            )
                        )
                    )
                }
                response.code() == 429 -> {
                    NewsResult.Error(
                        HeadlinesError(
                            resourceProvider.string(R.string.error_headlines_to_many, category.name)
                        )
                    )
                }
                else -> {
                    NewsResult.Error(
                        HeadlinesError(
                            resourceProvider.string(R.string.error_headlines_unknown)
                        )
                    )
                }
            }
        }
    }
}