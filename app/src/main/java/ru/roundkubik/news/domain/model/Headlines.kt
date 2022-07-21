package ru.roundkubik.news.domain.model

data class Headlines(
    val category: Category,
    val articles: List<Article>
)