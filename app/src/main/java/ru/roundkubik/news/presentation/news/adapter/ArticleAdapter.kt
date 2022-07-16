package ru.roundkubik.news.presentation.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.roundkubik.news.databinding.ItemArticleCardBinding
import ru.roundkubik.news.presentation.model.ArticleUi

class ArticleAdapter(
    private val onArticleClick: (urlToArticle: String) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var articles: List<ArticleUi> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            ItemArticleCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(onArticleClick, binding)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    fun submitData(data: List<ArticleUi>) {
        articles = data
    }

    class ArticleViewHolder(
        onArticleClick: (urlToArticle: String) -> Unit,
        private val binding: ItemArticleCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var item: ArticleUi

        init {
            binding.root.setOnClickListener {
                onArticleClick.invoke(item.url)
            }
        }

        fun bind(item: ArticleUi) {
            this.item = item
            binding.itemArticleCardIvContent.load(item.urlToImage)
            binding.itemArticleCardTvContent.text = item.title
        }
    }


}