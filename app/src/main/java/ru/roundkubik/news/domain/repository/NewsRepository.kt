package ru.roundkubik.news.domain.repository

import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.domain.model.Headlines

interface NewsRepository {
    fun getHeadlines(): NewsResult<Headlines>
}