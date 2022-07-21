package ru.roundkubik.news.data.source

import ru.roundkubik.news.data.cache.dao.ArticleDao

interface ArticleCacheDataSource {
    fun articleDao(): ArticleDao
}