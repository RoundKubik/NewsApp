package ru.roundkubik.news.data.cache.source

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.roundkubik.news.data.source.ArticleCacheDataSource
import ru.roundkubik.news.data.cache.ArticlesDb
import ru.roundkubik.news.data.cache.dao.ArticleDao
import javax.inject.Inject

class ArticleDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dbName: String
) : ArticleCacheDataSource {

    @Volatile
    private var room : ArticlesDb = Room.databaseBuilder(
        context,
        ArticlesDb::class.java,
        dbName
    ).build()

    @Synchronized
    override fun articleDao(): ArticleDao {
        synchronized(lock) {
            return room.articleDao()
        }
    }

    companion object {
        private val lock = Object()
    }
}