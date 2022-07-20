package ru.roundkubik.news.data.source

import ru.roundkubik.news.domain.model.Category

interface CategoryDataSource {
    fun getSortedCategories(): List<Category>
}