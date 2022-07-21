package ru.roundkubik.news.core.resource_provider

import androidx.annotation.StringRes

interface ResourceProvider {

    fun string(@StringRes id: Int): String

    fun string(@StringRes id: Int, vararg args: Any): String
}