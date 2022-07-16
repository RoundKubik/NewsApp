package ru.roundkubik.news.presentation.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.roundkubik.news.databinding.ItemNewsCategoryBinding
import ru.roundkubik.news.presentation.model.ArticleUi
import ru.roundkubik.news.presentation.model.HeadlineUi

class HeadlinesAdapter(
    private val onArticleClick: (urlToArticle: String) -> Unit
) : RecyclerView.Adapter<HeadlinesAdapter.HeadLinesViewHolder>() {

    private var headlines: List<HeadlineUi> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadLinesViewHolder {
        val binding =
            ItemNewsCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeadLinesViewHolder(onArticleClick, binding)
    }

    override fun onBindViewHolder(holder: HeadLinesViewHolder, position: Int) {
        val item = headlines[position]
        when(item) {
            is HeadlineUi.BaseHeadlineUi -> holder.bind(item.articles)
        }
    }

    override fun getItemCount(): Int = headlines.size

    fun submitData(data: List<HeadlineUi>) {
        headlines = data
    }

    class HeadLinesViewHolder(
        onArticleClick: (urlToArticle: String) -> Unit,
        binding: ItemNewsCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val articleAdapter: ArticleAdapter = ArticleAdapter(onArticleClick)

        init {
            binding.itemNewsCategoryRvNews.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.itemNewsCategoryRvNews.adapter = articleAdapter

        }

        fun bind(data: List<ArticleUi>) {
            articleAdapter.submitData(data)
        }

    }

}