package ru.roundkubik.news.presentation.model

import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines

data class BaseHeadlineUi(
    val articles: List<ArticleUi>,
    override val category: Category,
) : HeadlineUi(category)

fun Headlines.toHeadlineUi(): HeadlineUi {
    return BaseHeadlineUi(articles.map { it.toArticleUi() }, category)
}