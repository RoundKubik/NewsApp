package ru.roundkubik.news.domain.model

sealed class Category(val category: String) {
    object Business: Category("business")
    object Entertainment: Category("entertainment")
    object General: Category("general")
    object Health : Category("health")
    object Science : Category("science")
    object Technology : Category("technology")
}