package ru.roundkubik.news.core.entity

sealed class NewsResult<T> {
    data class Success<T>(val data: T) : NewsResult<T>()
    data class Error<T>(val error: ErrorEntity) : NewsResult<T>()
}