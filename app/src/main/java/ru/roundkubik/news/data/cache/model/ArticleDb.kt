package ru.roundkubik.news.data.cache.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.roundkubik.news.data.cache.converter.DateConverter
import ru.roundkubik.news.domain.model.Article
import java.util.*

@Entity(
    tableName = "articles_table",
)
@TypeConverters(DateConverter::class)
data class ArticleDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val categoryIdentity: String,
    val categoryName: String,
    val title: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: Date,
) {
    constructor(
        categoryIdentity: String,
        categoryName: String,
        title: String,
        url: String,
        urlToImage: String?,
        publishedAt: Date
    ) : this(0, categoryIdentity, categoryName, title, url, urlToImage, publishedAt)
}

fun ArticleDb.toArticle(): Article = Article(title, url, urlToImage, publishedAt)