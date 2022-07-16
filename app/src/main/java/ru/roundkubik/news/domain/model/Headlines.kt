package ru.roundkubik.news.domain.model

import java.util.*

data class Headlines(
    val id: UUID,
    val category: Category,
    val articles: List<Article>
)