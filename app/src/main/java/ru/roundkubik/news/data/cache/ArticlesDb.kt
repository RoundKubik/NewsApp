package ru.roundkubik.news.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.roundkubik.news.data.cache.dao.ArticlesDao
import ru.roundkubik.news.data.cache.model.ArticleDb

@Database(
    entities = [ArticleDb::class],
    version = 1,
    exportSchema = false
)
abstract class ArticlesDb : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao
}

