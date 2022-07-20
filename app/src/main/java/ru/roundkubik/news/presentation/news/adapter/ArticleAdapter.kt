package ru.roundkubik.news.presentation.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.roundkubik.news.core.root.adapter.BaseAdapter
import ru.roundkubik.news.core.root.adapter.BaseViewHolder
import ru.roundkubik.news.databinding.ItemArticleCardBinding
import ru.roundkubik.news.databinding.ItemArticleErrorBinding
import ru.roundkubik.news.databinding.ItemArticleProgressBinding
import ru.roundkubik.news.presentation.model.ArticleUi
import ru.roundkubik.news.presentation.model.ArticleUiMapper

class ArticleAdapter(
    private val onArticleClick: (urlToArticle: String) -> Unit
) : BaseAdapter<ArticleUi, BaseViewHolder<ArticleUi>>() {


    override fun getItemViewType(position: Int) = when (list[position]) {
        is ArticleUi.BaseArticleUi -> 0
        is ArticleUi.Progress -> 1
        is ArticleUi.Fail -> 2
        else -> -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ArticleUi> {
        when (viewType) {
            0 -> {
                val binding =
                    ItemArticleCardBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ArticleViewHolder(onArticleClick, binding)
            }
            1 -> {
                val binding = ItemArticleProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ProgressArticleViewHolder(binding)
            }
            2 -> {
                val binding = ItemArticleErrorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ErrorViewHolder(binding)
            }
            else -> throw IllegalStateException("unknown viewType $viewType")
        }

    }
    override fun diffUtilCallback(
        list: ArrayList<ArticleUi>,
        data: List<ArticleUi>
    ): DiffUtil.Callback = ArticleDiffUtilCallback(list, data, ArticleUiMapper())


    inner class ArticleDiffUtilCallback(
        private val old: List<ArticleUi>,
        private val new: List<ArticleUi>,
        private val same: ArticleUiMapper<ArticleUi>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = old.size

        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return same.isItemsSame(old[oldItemPosition], new[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            old[oldItemPosition] == new[newItemPosition]


    }
}