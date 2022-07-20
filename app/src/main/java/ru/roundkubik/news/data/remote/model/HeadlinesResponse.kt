package ru.roundkubik.news.data.remote.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines
import java.util.*

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
        UUID.randomUUID(),
        category,
        articles.map { it.toArticle() }.sortedByDescending { it.publishedAt }
    )
}