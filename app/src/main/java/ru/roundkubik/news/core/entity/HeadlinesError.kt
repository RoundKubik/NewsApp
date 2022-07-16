package ru.roundkubik.news.core.entity

sealed class HeadlinesError : ErrorEntity {
    object HeadlinesNotFound : HeadlinesError()
    object Unknown : HeadlinesError()
}