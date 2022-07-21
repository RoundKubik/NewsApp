package ru.roundkubik.news.presentation.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.roundkubik.news.R
import ru.roundkubik.news.databinding.FragmentNewsBinding
import ru.roundkubik.news.presentation.news.adapter.headlines.HeadlinesAdapter


@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private var headlinesAdapter: HeadlinesAdapter = HeadlinesAdapter {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        setupAdapter()
        binding.frgNewsSwl.setOnRefreshListener {
            viewModel.loadHeadLines()
            binding.frgNewsSwl.isRefreshing = false
        }
        viewModel.startListeningInternetConnection()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribe()
        viewModel.loadHeadLines()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopListeningInternetConnection()
    }

    private fun setupAdapter() {
        binding.frgNewsRvNewsCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.frgNewsRvNewsCategories.adapter = headlinesAdapter
    }

    private fun subscribe() {
        lifecycleScope.launch {
            viewModel.headlines.collect {
                headlinesAdapter.submitData(it.values.toList())
                viewModel.cacheHeadlines()
            }
        }
        lifecycleScope.launch {
            viewModel.newsState.collect {
                handleUiState(it)
            }
        }
    }

    private fun handleUiState(uiState: NewsViewModel.NewsState) {
        when (uiState) {
            is NewsViewModel.NewsState.NetworkNotAvailable -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_network_is_not_available),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is NewsViewModel.NewsState.NetworkAvailable -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_network_is_available),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is NewsViewModel.NewsState.FailureThrowable -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_headlines_unknown),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        private val TAG = NewsFragment::class.simpleName
    }
}