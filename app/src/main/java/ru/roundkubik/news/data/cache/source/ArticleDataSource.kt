package ru.roundkubik.news.data.cache.source

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.data.cache.ArticlesDb
import ru.roundkubik.news.data.cache.converter.DateConverter
import ru.roundkubik.news.data.cache.dao.ArticlesDao
import ru.roundkubik.news.data.cache.model.ArticleDb
import ru.roundkubik.news.data.cache.model.toArticle
import ru.roundkubik.news.data.source.ArticleCacheDataSource
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines
import javax.inject.Inject

class ArticleDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dbName: String
) : ArticleCacheDataSource {

    @Volatile
    private var room: ArticlesDb = Room.databaseBuilder(
        context,
        ArticlesDb::class.java,
        dbName
    ).addTypeConverter(DateConverter()).build()


    override fun articlesDao(): ArticlesDao {
        synchronized(lock) {
            return room.articlesDao()
        }
    }

    override fun fetchArticles(
        category: Category,
        maxPageSize: Int
    ): Single<NewsResult<Headlines>> {
        synchronized(lock) {
            return articlesDao().fetchArticles(
                categoryName = category.name,
                categoryIdentity = category.identity,
                count = maxPageSize
            ).map {
                NewsResult.Success(
                    Headlines(
                        category,
                        it.map { articleDb -> articleDb.toArticle() }
                    )
                )
            }
        }
    }

    override fun addArticles(headlines: Headlines) {
        synchronized(lock) {
            articlesDao().addArticles(
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
            )
        }
    }

    override fun deleteArticles(headlines: Headlines) {
        synchronized(lock) {
            articlesDao().deleteAll(
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
            )
        }
    }


    companion object {
        private val lock = Object()
    }

}