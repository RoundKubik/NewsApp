package ru.roundkubik.news.data.cache.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "articles_table",
    indices = [Index(value = ["fileId"], unique = true)]
)
data class ArticleDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val fileName: String,
    val fileId: Long,
    val filePath: String,
    val fileUri: String,
)