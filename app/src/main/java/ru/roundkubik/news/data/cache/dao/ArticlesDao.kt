package ru.roundkubik.news.data.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single
import ru.roundkubik.news.data.cache.model.ArticleDb

@Dao
interface ArticlesDao {

    @Insert
    fun addArticles(articles: List<ArticleDb>): Single<List<Long>>

    @Query("SELECT * FROM ARTICLES_TABLE AT WHERE AT.categoryIdentity = :categoryIdentity AND AT.categoryName = :categoryName LIMIT :count")
    fun fetchArticles(
        categoryIdentity: String,
        categoryName: String,
        count: Int
    ): Single<List<ArticleDb>>

    @Delete
    fun deleteAll(articles: List<ArticleDb>)
}