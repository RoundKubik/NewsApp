package ru.roundkubik.news.data.source

import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.domain.model.Headlines

interface NewsDataSource {
    fun getHeadlines(): NewsResult<Headlines>
}