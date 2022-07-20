package ru.roundkubik.news.domain.usecase

import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.repository.CategoryRepository

class GetSortedCategoriesUseCase(
    private val categoryRepository: CategoryRepository,
) {
    operator fun invoke(): List<Category> {
        return categoryRepository.getSortedCategories()
    }
}