package ru.roundkubik.news.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.roundkubik.news.data.cache.model.ArticleDb

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticle(file: ArticleDb)

 /*   @Query("SELECT * FROM ARTICLES_TABLE AT WHERE FT.fileId = :fileId LIMIT 1")
    suspend fun getArticles()
*/
}