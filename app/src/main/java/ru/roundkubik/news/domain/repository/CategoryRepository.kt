package ru.roundkubik.news.domain.repository

import ru.roundkubik.news.domain.model.Category

interface CategoryRepository {
    fun getSortedCategories() : List<Category>
}