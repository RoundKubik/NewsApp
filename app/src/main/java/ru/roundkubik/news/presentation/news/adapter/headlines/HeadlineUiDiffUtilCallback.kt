package ru.roundkubik.news.presentation.news.adapter.headlines

import androidx.recyclerview.widget.DiffUtil
import ru.roundkubik.news.presentation.model.HeadlineUi

class HeadlineUiDiffUtilCallback(
    private val old: List<HeadlineUi>,
    private val new: List<HeadlineUi>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].category == new[newItemPosition].category
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition] == new[newItemPosition]

}