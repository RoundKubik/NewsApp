package ru.roundkubik.news.data.cache.dao

import androidx.room.*
import io.reactivex.Single
import ru.roundkubik.news.data.cache.model.ArticleDb

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArticles(articles: List<ArticleDb>)

    @Query("SELECT * FROM ARTICLES_TABLE AT WHERE AT.categoryIdentity = :categoryIdentity AND AT.categoryName = :categoryName ORDER BY publishedAt DESC LIMIT :count")
    fun fetchArticles(
        categoryIdentity: String,
        categoryName: String,
        count: Int
    ): Single<List<ArticleDb>>

    @Delete
    fun deleteAll(articles: List<ArticleDb>)
}