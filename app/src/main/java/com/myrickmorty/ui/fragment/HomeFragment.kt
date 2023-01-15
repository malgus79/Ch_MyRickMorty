package com.myrickmorty.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.myrickmorty.R
import com.myrickmorty.core.Resource
import com.myrickmorty.core.common.hide
import com.myrickmorty.core.common.show
import com.myrickmorty.core.common.showToast
import com.myrickmorty.databinding.FragmentHomeBinding
import com.myrickmorty.ui.adapter.HomeAdapter
import com.myrickmorty.viewmodel.fragment.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeAdapter = HomeAdapter()

        setupCharacters()

        return binding.root
    }

    private fun setupCharacters() {
        viewModel.fetchCharacters().observe(viewLifecycleOwner, Observer {
            when (it) {
                Resource.Loading -> {
                    binding.containerLoading.root.show()
                }
                is Resource.Success -> {
                    if (it.data.results.isEmpty()) {
                        binding.containerLoading.root.show()
                        return@Observer
                    }
                    binding.containerLoading.root.hide()
                    setupRecyclerView()
                    loadData()
                }
                is Resource.Failure -> {
                    binding.containerLoading.root.hide()
                    showToast(getString(R.string.error_dialog_detail) + it.exception)

                    val handlerTimer = Handler()
                    handlerTimer.postDelayed({
                        showErrorDialog(callback = { setupCharacters() })
                    }, 1000)
                }
            }
        })
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = homeAdapter
            layoutManager = StaggeredGridLayoutManager(
                resources.getInteger(R.integer.main_columns_home),
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.listData.collect {
                Log.d("aaa", "load: $it")
                homeAdapter.submitData(it)
            }
        }
    }

    private fun showErrorDialog(callback: (() -> Unit)? = null) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.error_dialog))
            .setMessage(getString(R.string.error_dialog_detail))
            .setPositiveButton(getString(R.string.try_again)) { _, _ -> callback?.invoke() }
            .setNegativeButton(getString(R.string.ok)) { _, _ -> binding.containerLoading.root.show() }
            .setCancelable(false)
            .show()
    }
}