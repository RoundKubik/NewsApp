package ru.roundkubik.news.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.roundkubik.news.databinding.FragmentNewsBinding
import ru.roundkubik.news.presentation.news.adapter.HeadlinesAdapter

class NewsFragment : Fragment() {

    lateinit var viewModel: NewsViewModel

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private var headlinesAdapter: HeadlinesAdapter = HeadlinesAdapter {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        viewModel.getHeadlines()
        setupAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribe()
        handleUiState()
    }

    private fun setupAdapter() {
        binding.frgNewsRvNewsCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.frgNewsRvNewsCategories.adapter = headlinesAdapter
    }

    private fun handleUiState() {

    }

    private fun subscribe() {
        lifecycleScope.launch {
            viewModel.headlines.collectLatest {
                 headlinesAdapter.submitData(it.values.toList())
            }
        }
    }
}