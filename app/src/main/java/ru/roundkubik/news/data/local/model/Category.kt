package ru.roundkubik.news.data.local.model

import androidx.annotation.StringRes
import ru.roundkubik.news.R

sealed class CategoryData(
    @StringRes val identityId: Int,
    @StringRes val resId: Int
) {
    object Business : CategoryData(R.string.category_identity_business, R.string.category_business)
    object Entertainment :
        CategoryData(R.string.category_entertainment, R.string.category_entertainment)

    object General :
        CategoryData(R.string.category_identity_general, R.string.category_general)

    object Health : CategoryData(R.string.category_identity_health, R.string.category_health)
    object Science : CategoryData(R.string.category_identity_science, R.string.category_science)
    object Technology :
        CategoryData(R.string.category_identity_technology, R.string.category_technology)

    companion object {
        fun getCategoriesList(): List<CategoryData> = listOf(
           Business,
           Entertainment,
           General,
           Health,
           Science,
           Technology
        )
    }
}