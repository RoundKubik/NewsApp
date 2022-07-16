package ru.roundkubik.news.domain.usecase

import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.repository.CategoryRepository
import ru.roundkubik.news.domain.repository.CountryRepository

class GetSortedCategories(
    private val categoryRepository: CategoryRepository,
    private val countryRepository: CountryRepository
) {
    operator fun invoke(): List<Category> {
        return categoryRepository.getSortedCategories(countryRepository.getCountry())
    }
}