package ru.roundkubik.news.domain.model

sealed class Country(val country: String) {
    object Russia: Country("ru")
    object Us: Country("us")
}