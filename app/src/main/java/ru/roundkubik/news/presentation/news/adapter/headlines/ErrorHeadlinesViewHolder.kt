package ru.roundkubik.news.presentation.news.adapter.headlines

import ru.roundkubik.news.core.root.adapter.BaseViewHolder
import ru.roundkubik.news.databinding.ItemHeadlinesErrorBinding
import ru.roundkubik.news.presentation.model.HeadlineUi

class ErrorHeadlinesViewHolder(
    private val binding: ItemHeadlinesErrorBinding
) : BaseViewHolder.Fail<HeadlineUi>(binding.root) {

    override fun bind(elem: HeadlineUi) {
        binding.itemHeadlinesErrorTvCategory.text = elem.category.name.uppercase()
        if (elem is HeadlineUi.Fail) {
            binding.itemHeadlinesErrorTvErrorMessage.text = elem.message
        }
    }
}