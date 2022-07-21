package ru.roundkubik.news.data.cache.source

import android.content.Context
import android.util.Log
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import ru.roundkubik.news.R
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.core.resource_provider.ResourceProvider
import ru.roundkubik.news.data.cache.ArticlesDb
import ru.roundkubik.news.data.cache.dao.ArticlesDao
import ru.roundkubik.news.data.cache.model.ArticleDb
import ru.roundkubik.news.data.cache.model.toArticle
import ru.roundkubik.news.data.remote.model.HeadlinesError
import ru.roundkubik.news.data.source.ArticleCacheDataSource
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines
import javax.inject.Inject

class ArticleDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val resourceProvider: ResourceProvider,
    private val dbName: String
) : ArticleCacheDataSource {

    @Volatile
    private var room: ArticlesDb = Room.databaseBuilder(
        context,
        ArticlesDb::class.java,
        dbName
    ).build()

    override fun articlesDao(): ArticlesDao {
        return room.articlesDao()
    }

    override fun fetchArticles(
        category: Category,
        maxPageSize: Int
    ): Single<NewsResult<Headlines>> {
        return articlesDao().fetchArticles(
            categoryIdentity = category.identity,
            categoryName = category.name,
            maxPageSize
        ).map {
            if (it.isNotEmpty()) {
                NewsResult.Success(
                    Headlines(
                        category,
                        it.map { articleDb -> articleDb.toArticle() }
                    )
                )
            } else {
                NewsResult.Error(
                    HeadlinesError(
                        resourceProvider.string(
                            R.string.error_headlines_empty_headlines,
                            category.name
                        )
                    )
                )
            }
        }
    }

    override fun addArticles(headlines: Headlines): Single<NewsResult<List<Long>>> {
        Log.d("ArticleDataSource", "addArticles: ${headlines.articles}")
        return articlesDao().addArticles(
            headlines.articles.map {
                ArticleDb(
                    categoryIdentity = headlines.category.identity,
                    categoryName = headlines.category.name,
                    title = it.title,
                    url = it.url,
                    urlToImage = it.urlToImage,
                    publishedAt = it.publishedAt
                )
            }
        ).map {
            NewsResult.Success(it)
        }
    }

    companion object {
        private val lock = Object()
    }

}