package ru.roundkubik.news.data.remote.model

import com.google.gson.annotations.SerializedName
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

fun HeadlinesResponse.toHeadlines() : Headlines {
    return Headlines(UUID.randomUUID(), "", articles.map { it.toArticle()})
}