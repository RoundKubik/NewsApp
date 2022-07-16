package ru.roundkubik.news.presentation.model

import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines
import java.util.*



abstract class HeadlineUi(
    val category: Category
) {

    class Empty(category: Category) : HeadlineUi(category)

    class Progress(category: Category) : HeadlineUi(category)

    data class BaseHeadlineUi(
        val id: UUID,
        val cat: Category,
        val articles: List<ArticleUi>
    ): HeadlineUi(cat)

    data class Fail(val message: String, val cat: Category): HeadlineUi(cat)
}

fun Headlines.toHeadlineUi() : HeadlineUi {
    return HeadlineUi.BaseHeadlineUi(id, category, articles.map { it.toArticleUi() })
}