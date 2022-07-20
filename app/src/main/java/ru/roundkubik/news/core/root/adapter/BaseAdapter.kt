package ru.roundkubik.news.core.root.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<E, T : BaseViewHolder<E>> : RecyclerView.Adapter<T>() {

    protected val list = ArrayList<E>()

    fun submitData(data: List<E>) {
        val diffCallback = diffUtilCallback(list, data)
        val result = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(data)
        result.dispatchUpdatesTo(this)
    }

    abstract fun diffUtilCallback(list: ArrayList<E>, data: List<E>): DiffUtil.Callback

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: T, position: Int) =
        holder.bind(list[position])

    protected fun Int.makeView(parent: ViewGroup) = LayoutInflater.from(parent.context).inflate(this, parent, false)
}