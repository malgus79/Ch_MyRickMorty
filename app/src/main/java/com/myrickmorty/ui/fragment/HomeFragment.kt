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
import com.myrickmorty.core.hide
import com.myrickmorty.core.show
import com.myrickmorty.core.showToast
import com.myrickmorty.databinding.FragmentHomeBinding
import com.myrickmorty.ui.adapter.CharacterAdapter
import com.myrickmorty.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var characterAdapter: CharacterAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        characterAdapter = CharacterAdapter()

        setupCharacters()

        return binding.root
    }

    private fun setupCharacters() {
        viewModel.fetchCharacters().observe(viewLifecycleOwner, Observer {
            when (it) {
                Resource.Loading -> {
                    binding.progressBar.show()
                }
                is Resource.Success -> {
                    if (it.data.results.isNullOrEmpty()) {
                        binding.progressBar.show()
                        return@Observer
                    }
                    binding.progressBar.hide()
                    setupRecyclerView()
                    loadData()
                }
                is Resource.Failure -> {
                    binding.progressBar.hide()
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
            adapter = characterAdapter
            layoutManager = StaggeredGridLayoutManager(
                resources.getInteger(R.integer.main_columns),
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
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

    private fun showErrorDialog(callback: (() -> Unit)? = null) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.error_dialog))
            .setMessage(getString(R.string.error_dialog_detail))
            .setPositiveButton(getString(R.string.try_again)) { _, _ -> callback?.invoke() }
            .setNegativeButton(getString(R.string.ok)) { _, _ -> binding.progressBar.show() }
            .setCancelable(false)
            .show()
    }
}