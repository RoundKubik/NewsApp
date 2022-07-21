package ru.roundkubik.news.core.root.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<E>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(elem: E)


    abstract class Base<E>(view: View) : BaseViewHolder<E>(view) {
    }

    abstract class Progress<E>(view: View) : BaseViewHolder<E>(view) {
    }

    abstract class Fail<E>(view: View) : BaseViewHolder<E>(view) {
    }
}