package ru.roundkubik.news.presentation.model

import ru.roundkubik.news.domain.model.Article
import java.util.*

data class ArticleUi(
    val id: UUID,
    val title: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Date
)

fun Article.toArticleUi() : ArticleUi {
    return ArticleUi(id, title, url, urlToImage, publishedAt)
}
