package ru.roundkubik.news.presentation.news.adapter.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.roundkubik.news.core.root.presentation.adapter.BaseAdapter
import ru.roundkubik.news.core.root.presentation.adapter.BaseViewHolder
import ru.roundkubik.news.databinding.ItemArticleCardBinding
import ru.roundkubik.news.presentation.model.ArticleUi

class ArticleAdapter(
    private val onArticleClick: (urlToArticle: String) -> Unit
) : BaseAdapter<ArticleUi, BaseViewHolder<ArticleUi>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ArticleUi> {
        val binding = ItemArticleCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(onArticleClick, binding)

    }

    override fun diffUtilCallback(
        list: ArrayList<ArticleUi>,
        data: List<ArticleUi>
    ): DiffUtil.Callback = ArticleDiffUtilCallback(list, data)


}