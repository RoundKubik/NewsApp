package ru.roundkubik.news.presentation.news.adapter.headlines

import ru.roundkubik.news.core.root.adapter.BaseViewHolder
import ru.roundkubik.news.databinding.ItemHeadlinesProgressBinding
import ru.roundkubik.news.presentation.model.HeadlineUi

class ProgressHeadlinesViewHolder(private val binding: ItemHeadlinesProgressBinding) :
    BaseViewHolder.Progress<HeadlineUi>(binding.root) {

    override fun bind(elem: HeadlineUi) {
        binding.itemArticleProgressTvCategory.text = elem.category.name.uppercase()
    }

}