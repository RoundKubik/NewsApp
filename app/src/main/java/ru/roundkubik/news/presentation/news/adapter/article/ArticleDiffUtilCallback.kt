package ru.roundkubik.news.presentation.news.adapter.article

import androidx.recyclerview.widget.DiffUtil
import ru.roundkubik.news.presentation.model.ArticleUi

class ArticleDiffUtilCallback(
    private val old: List<ArticleUi>,
    private val new: List<ArticleUi>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].url == new[newItemPosition].url
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition] == new[newItemPosition]


}