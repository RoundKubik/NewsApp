package ru.roundkubik.news.presentation.model

import ru.roundkubik.news.domain.model.Category

data class FailHeadlineUi(
    val message: String,
    override val category: Category
) : HeadlineUi(category)