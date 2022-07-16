package ru.roundkubik.news.domain.repository

import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Country

interface CategoryRepository {
    fun getSortedCategories(lan: Country) : List<Category>
}