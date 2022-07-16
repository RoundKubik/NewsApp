package ru.roundkubik.news.presentation.model

import java.util.*

data class HeadlineUi(
    val id: UUID,
    val category: String,
    val articles: List<ArticleUi>
)