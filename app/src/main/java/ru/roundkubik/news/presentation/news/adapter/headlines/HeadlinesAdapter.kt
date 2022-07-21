package ru.roundkubik.news.presentation.news.adapter.headlines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.roundkubik.news.core.root.presentation.adapter.BaseAdapter
import ru.roundkubik.news.core.root.presentation.adapter.BaseViewHolder
import ru.roundkubik.news.databinding.ItemHeadlinesBinding
import ru.roundkubik.news.databinding.ItemHeadlinesErrorBinding
import ru.roundkubik.news.databinding.ItemHeadlinesProgressBinding
import ru.roundkubik.news.presentation.model.BaseHeadlineUi
import ru.roundkubik.news.presentation.model.FailHeadlineUi
import ru.roundkubik.news.presentation.model.HeadlineUi
import ru.roundkubik.news.presentation.model.ProgressHeadlineUi

class HeadlinesAdapter(
    private val onArticleClick: (urlToArticle: String) -> Unit
) : BaseAdapter<HeadlineUi, BaseViewHolder<HeadlineUi>>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun getItemViewType(position: Int) = when (list[position]) {
        is BaseHeadlineUi -> 0
        is ProgressHeadlineUi -> 1
        is FailHeadlineUi -> 2
        else -> -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HeadlineUi> {
        when (viewType) {
            0 -> {
                val binding =
                    ItemHeadlinesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HeadLinesViewHolder(
                    binding,
                    onArticleClick,
                    viewPool
                )
            }
            1 -> {
                val binding =
                    ItemHeadlinesProgressBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ProgressHeadlinesViewHolder(binding)
            }
            2 -> {
                val binding =
                    ItemHeadlinesErrorBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ErrorHeadlinesViewHolder(binding)
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
    ): DiffUtil.Callback = HeadlineUiDiffUtilCallback(list, data)
}
