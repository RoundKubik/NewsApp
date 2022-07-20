package ru.roundkubik.news.data.repository

import ru.roundkubik.news.data.source.CategoryDataSource
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDataSource: CategoryDataSource
): CategoryRepository {
    override fun getSortedCategories(): List<Category> = categoryDataSource.getSortedCategories()
}