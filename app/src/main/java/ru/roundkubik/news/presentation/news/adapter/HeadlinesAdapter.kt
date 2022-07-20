package ru.roundkubik.news.presentation.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.roundkubik.news.core.root.adapter.BaseAdapter
import ru.roundkubik.news.core.root.adapter.BaseViewHolder
import ru.roundkubik.news.databinding.ItemArticleErrorBinding
import ru.roundkubik.news.databinding.ItemArticleProgressBinding
import ru.roundkubik.news.databinding.ItemNewsCategoryBinding
import ru.roundkubik.news.presentation.model.ArticleUi
import ru.roundkubik.news.presentation.model.ArticleUiMapper
import ru.roundkubik.news.presentation.model.HeadlineUi
import ru.roundkubik.news.presentation.model.HeadlineUiMapper

class HeadlinesAdapter(
    private val onArticleClick: (urlToArticle: String) -> Unit
) : BaseAdapter<HeadlineUi, BaseViewHolder<HeadlineUi>>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun getItemViewType(position: Int) = when (list[position]) {
        is HeadlineUi.BaseHeadlineUi -> 0
        is HeadlineUi.Progress -> 1
        is HeadlineUi.Fail -> 2
        else -> -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HeadlineUi> {
        when (viewType) {
            0 -> {
                val binding =
                    ItemNewsCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return BaseHeadLinesViewHolder.HeadLinesViewHolder(binding, onArticleClick, viewPool)
            }
            1 -> {
                val binding =
                    ItemArticleProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return BaseHeadLinesViewHolder.ProgressHeadlinesViewHolder(binding)
            }
            2 -> {
                val binding =
                    ItemArticleErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return BaseHeadLinesViewHolder.ErrorHeadlinesViewHolder(binding)
            }
            else -> throw IllegalStateException("unknown viewType $viewType")
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder<HeadlineUi>, position: Int) {
        holder.bind(list[position])
    }


    override fun diffUtilCallback(
        list: ArrayList<HeadlineUi>,
        data: List<HeadlineUi>
    ): DiffUtil.Callback = HeadlineUiDiffUtilCallback(list, data, HeadlineUiMapper())


    inner class HeadlineUiDiffUtilCallback(
        private val old: List<HeadlineUi>,
        private val new: List<HeadlineUi>,
        private val same: HeadlineUiMapper<HeadlineUi>
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