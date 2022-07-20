package ru.roundkubik.news.presentation.news.adapter

import coil.load
import ru.roundkubik.news.core.root.adapter.BaseViewHolder
import ru.roundkubik.news.databinding.ItemArticleCardBinding
import ru.roundkubik.news.databinding.ItemArticleErrorBinding
import ru.roundkubik.news.databinding.ItemArticleProgressBinding
import ru.roundkubik.news.presentation.model.ArticleUi

class ArticleViewHolder(
    onArticleClick: (urlToArticle: String) -> Unit,
    private val binding: ItemArticleCardBinding
) : BaseViewHolder<ArticleUi>(binding.root) {

    private lateinit var item: ArticleUi.BaseArticleUi

    init {
        binding.root.setOnClickListener {
            onArticleClick.invoke(item.url)
        }
    }

    override fun bind(elem: ArticleUi) {
        if (elem is ArticleUi.BaseArticleUi) {
            item = elem
            binding.itemArticleCardIvContent.load(item.urlToImage)
            binding.itemArticleCardTvContent.text = item.title
        }
    }
}

class ProgressArticleViewHolder(
    private val binding: ItemArticleProgressBinding
) : BaseViewHolder.Progress<ArticleUi>(binding.root) {
    override fun bind(elem: ArticleUi) {
    }

}

class ErrorViewHolder(
    private val binding: ItemArticleErrorBinding
) : BaseViewHolder.Fail<ArticleUi>(binding.root) {
    override fun bind(elem: ArticleUi) {
    }

}
