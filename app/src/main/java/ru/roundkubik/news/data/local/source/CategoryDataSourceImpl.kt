package ru.roundkubik.news.data.local.source

import ru.roundkubik.news.core.resource_provider.ResourceProvider
import ru.roundkubik.news.data.local.model.CategoryData
import ru.roundkubik.news.data.source.CategoryDataSource
import ru.roundkubik.news.domain.model.Category
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor(
    private val resourceProvider: ResourceProvider
) : CategoryDataSource {

    override fun getSortedCategories(): List<Category> {
        return CategoryData.getCategoriesList()
            .map {
                Category(
                    resourceProvider.string(it.identityId),
                    resourceProvider.string(it.resId)
                )
            }
            .sortedBy { it.name }
    }
}