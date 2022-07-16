package ru.roundkubik.news.presentation.model

import java.util.*

data class ArticleUi(
    val id: UUID,
    val title: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Date
)