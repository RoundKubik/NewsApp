package ru.roundkubik.news.core.resource_provider

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

interface ResourceProvider {

    fun string(@StringRes id: Int): String

    fun stringArray(@ArrayRes id: Int): Array<String>
}