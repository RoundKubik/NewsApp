package ru.roundkubik.news.presentation.model

import ru.roundkubik.news.domain.model.Category

data class ProgressHeadlineUi(override val category: Category) : HeadlineUi(category)