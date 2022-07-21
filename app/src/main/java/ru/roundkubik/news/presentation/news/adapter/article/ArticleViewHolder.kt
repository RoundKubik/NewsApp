package ru.roundkubik.news.presentation.news.adapter.article

import coil.load
import ru.roundkubik.news.core.root.adapter.BaseViewHolder
import ru.roundkubik.news.databinding.ItemArticleCardBinding
import ru.roundkubik.news.presentation.model.ArticleUi

class ArticleViewHolder(
    onArticleClick: (urlToArticle: String) -> Unit,
    private val binding: ItemArticleCardBinding
) : BaseViewHolder<ArticleUi>(binding.root) {

    private lateinit var item: ArticleUi

    init {
        binding.root.setOnClickListener {
            onArticleClick.invoke(item.url)
        }
    }

    override fun bind(elem: ArticleUi) {
        item = elem
        binding.itemArticleCardIvContent.load(item.urlToImage)
        binding.itemArticleCardTvContent.text = item.title
    }
}

