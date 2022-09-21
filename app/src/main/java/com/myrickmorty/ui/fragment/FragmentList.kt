package com.myrickmorty.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.myrickmorty.R
import com.myrickmorty.databinding.FragmentListBinding
import com.myrickmorty.ui.adapter.CharacterAdapter
import com.myrickmorty.ui.adapter.LoaderStateAdapter
import com.myrickmorty.viewmodel.FragmentListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentList : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding
    private lateinit var characterAdapter: CharacterAdapter
    private val viewModel: FragmentListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)

        setupRecyclerView()
        //loadData()

        lifecycleScope.launchWhenStarted {
            viewModel.listData.collectLatest { response ->
                binding.apply {
                    progressBar.isVisible = false
                    recyclerView.isVisible = true
                }
                characterAdapter.submitData(response)
            }
        }
    }

    private fun setupRecyclerView(isLoad: Boolean = false) {
        characterAdapter = CharacterAdapter()

        binding.recyclerView.apply {
            adapter = characterAdapter.withLoadStateHeaderAndFooter(
                header = LoaderStateAdapter { characterAdapter::retry },
                footer = LoaderStateAdapter { characterAdapter::retry }
            )
            layoutManager = StaggeredGridLayoutManager(
                resources.getInteger(R.integer.main_columns),
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
        }
        characterAdapter.addLoadStateListener { loadState ->
            val refreshState =
                if (isLoad) {
                    loadState.refresh
                } else {
                    loadState.source.refresh
                }
            binding.recyclerView.isVisible = refreshState is LoadState.NotLoading
            binding.progressBar.isVisible = refreshState is LoadState.Loading
            binding.incSectionError.btnRetry.isVisible = refreshState is LoadState.Error
            handleError(loadState)
        }
        binding.incSectionError.btnRetry.setOnClickListener {
            characterAdapter.retry()
        }
    }

    private fun handleError(loadState: CombinedLoadStates) {
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error

        errorState?.let {
            Toast.makeText(requireContext(), "Ocurrio un error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.listData.collect {
                Log.d("aaa", "load: $it")
                characterAdapter.submitData(it)
            }
        }
    }
}