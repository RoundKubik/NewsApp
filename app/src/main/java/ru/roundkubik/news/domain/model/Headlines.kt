package ru.roundkubik.news.domain.model

import java.util.*

data class Headlines(
    val id: UUID,
    val category: String,
    val articles: List<Article>
)