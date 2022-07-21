package ru.roundkubik.news.core.resource_provider

import android.content.Context

class BaseResourceProvider(
    private val context: Context
) : ResourceProvider {

    override fun string(id: Int): String = context.getString(id)

    override fun string(id: Int, vararg args: Any): String = context.getString(id, *args)

}