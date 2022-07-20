package ru.roundkubik.news.presentation.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.roundkubik.news.databinding.FragmentNewsBinding
import ru.roundkubik.news.presentation.news.adapter.HeadlinesAdapter

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()

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

        setupAdapter()
        viewModel.getHeadlines()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribe()
    }

    private fun setupAdapter() {
        binding.frgNewsRvNewsCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.frgNewsRvNewsCategories.adapter = headlinesAdapter
    }

    private fun subscribe() {
        lifecycleScope.launch {
            viewModel.headlines.collect {
                headlinesAdapter.submitData(it.values.toList())
            }
        }
    }

    companion object {
        private val TAG = NewsFragment::class.simpleName
    }
}