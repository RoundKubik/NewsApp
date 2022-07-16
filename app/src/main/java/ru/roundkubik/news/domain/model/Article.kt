package ru.roundkubik.news.domain.model

import java.util.*

data class Article(
    val id: UUID,
    val title: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Date
)