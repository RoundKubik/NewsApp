package ru.roundkubik.news.data.remote.model

import com.google.gson.annotations.SerializedName
import ru.roundkubik.news.domain.model.Article
import java.util.*

data class ArticleResponse(
    @SerializedName("source")
    val source: SourceResponse,
    @SerializedName("author")
    val author: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("publishedAt")
    val publishedAt: Date,
    @SerializedName("content")
    val content: String
)

fun ArticleResponse.toArticle() : Article {
    return Article(
        id = UUID.randomUUID(),
        title = title,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt
    )
}