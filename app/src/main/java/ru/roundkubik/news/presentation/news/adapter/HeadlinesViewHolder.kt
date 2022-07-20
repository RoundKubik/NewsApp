package ru.roundkubik.news.presentation.news.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.roundkubik.news.core.root.adapter.BaseViewHolder
import ru.roundkubik.news.databinding.ItemArticleErrorBinding
import ru.roundkubik.news.databinding.ItemArticleProgressBinding
import ru.roundkubik.news.databinding.ItemNewsCategoryBinding
import ru.roundkubik.news.presentation.model.HeadlineUi


abstract class BaseHeadLinesViewHolder(
    view: View
) : BaseViewHolder<HeadlineUi>(view) {

    class HeadLinesViewHolder(
        private val binding: ItemNewsCategoryBinding,
        onArticleClick: (urlToArticle: String) -> Unit,
        viewPool: RecyclerView.RecycledViewPool
    ) : BaseViewHolder<HeadlineUi>(binding.root) {

        private val articleAdapter: ArticleAdapter = ArticleAdapter(onArticleClick)

        init {
            binding.itemNewsCategoryRvNews.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.itemNewsCategoryRvNews.adapter = articleAdapter
            binding.itemNewsCategoryRvNews.setRecycledViewPool(viewPool)
        }

        override fun bind(elem: HeadlineUi) {
            binding.itemNewsCategoryTvCategory.text = elem.category.category.uppercase()
            if (elem is HeadlineUi.BaseHeadlineUi) {
                articleAdapter.submitData(elem.articles)
            }
        }
    }

    class ErrorHeadlinesViewHolder(
        private val binding: ItemArticleErrorBinding
    ) : BaseViewHolder.Fail<HeadlineUi>(binding.root) {

        override fun bind(elem: HeadlineUi) {
            binding.itemArticleErrorTvErrorMessage.text = elem.category.category.uppercase()
        }
    }

    class ProgressHeadlinesViewHolder(private val binding: ItemArticleProgressBinding) :
        BaseViewHolder.Progress<HeadlineUi>(binding.root) {

        override fun bind(elem: HeadlineUi) {
            binding.itemArticleProgressTvCategory.text = elem.category.category.uppercase()
        }

    }

}
