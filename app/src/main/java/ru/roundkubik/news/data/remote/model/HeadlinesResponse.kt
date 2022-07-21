package ru.roundkubik.news.data.remote.model

import com.google.gson.annotations.SerializedName
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines

data class HeadlinesResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<ArticleResponse>
)

fun HeadlinesResponse.toHeadlines(category: Category): Headlines {
    return Headlines(
        category,
        articles.map { it.toArticle() }.sortedByDescending { it.publishedAt }
    )
}