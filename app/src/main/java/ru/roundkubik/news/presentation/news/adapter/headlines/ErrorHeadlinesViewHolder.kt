package ru.roundkubik.news.presentation.news.adapter.headlines

import ru.roundkubik.news.core.root.presentation.adapter.BaseViewHolder
import ru.roundkubik.news.databinding.ItemHeadlinesErrorBinding
import ru.roundkubik.news.presentation.model.FailHeadlineUi
import ru.roundkubik.news.presentation.model.HeadlineUi

class ErrorHeadlinesViewHolder(
    private val binding: ItemHeadlinesErrorBinding
) : BaseViewHolder.Fail<HeadlineUi>(binding.root) {

    override fun bind(elem: HeadlineUi) {
        binding.itemHeadlinesErrorTvCategory.text = elem.category.name.uppercase()
        if (elem is FailHeadlineUi) {
            binding.itemHeadlinesErrorTvErrorMessage.text = elem.message
        }
    }
}