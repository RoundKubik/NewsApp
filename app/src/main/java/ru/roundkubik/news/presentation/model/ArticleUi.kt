package ru.roundkubik.news.presentation.model

import ru.roundkubik.news.domain.model.Article
import ru.roundkubik.news.domain.model.Category
import java.util.*

abstract class ArticleUi {

    object Progress : ArticleUi()

    data class BaseArticleUi(
        val id: UUID,
        val title: String,
        val url: String,
        val urlToImage: String?,
        val publishedAt: Date
    ) : ArticleUi()

    data class Fail(val message: String) : ArticleUi()
}


class ArticleUiMapper<T : ArticleUi> {

    fun isItemsSame(old: T, new: T): Boolean {
        return if (old is ArticleUi.BaseArticleUi && new is ArticleUi.BaseArticleUi) {
            old.id == new.id
        } else false
    }

}

fun Article.toArticleUi(): ArticleUi {
    return ArticleUi.BaseArticleUi(id, title, url, urlToImage, publishedAt)
}
