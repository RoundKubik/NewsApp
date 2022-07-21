package ru.roundkubik.news.presentation.model

import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines


abstract class HeadlineUi(open val category: Category)  {

    data class Progress(override val category: Category) : HeadlineUi(category)

    data class BaseHeadlineUi(
        val articles: List<ArticleUi>,
        override val category: Category,
    ) : HeadlineUi(category)

    data class Fail(val message: String, override val category: Category) : HeadlineUi(category)
}

fun Headlines.toHeadlineUi(): HeadlineUi {
    return HeadlineUi.BaseHeadlineUi(articles.map { it.toArticleUi() }, category)
}
