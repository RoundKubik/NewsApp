package ru.roundkubik.news.presentation.news.adapter.headlines

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.roundkubik.news.core.root.presentation.adapter.BaseViewHolder
import ru.roundkubik.news.databinding.ItemHeadlinesBinding
import ru.roundkubik.news.presentation.model.BaseHeadlineUi
import ru.roundkubik.news.presentation.model.HeadlineUi
import ru.roundkubik.news.presentation.news.adapter.article.ArticleAdapter

class HeadLinesViewHolder(
    private val binding: ItemHeadlinesBinding,
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
        binding.itemNewsCategoryTvCategory.text = elem.category.name.uppercase()
        if (elem is BaseHeadlineUi) {
            articleAdapter.submitData(elem.articles)
        }
    }
}