package com.myrickmorty.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.myrickmorty.R
import com.myrickmorty.core.State
import com.myrickmorty.databinding.FragmentListBinding
import com.myrickmorty.model.data.ResponseApi
import com.myrickmorty.ui.adapter.CharacterAdapter
import com.myrickmorty.viewmodel.FragmentListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Response

@AndroidEntryPoint
class FragmentList : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding
    private lateinit var characterAdapter: CharacterAdapter
    private val viewModel: FragmentListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)

        setupRecyclerView()
        loadData()

        viewModel.getCharacters()
        viewModel.characterList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is State.Success<*> -> {
                    setCharacter(it.results)
                    showSpinnerLoading(false)
                }
                is State.Failure -> {
                    showErrorDialog(callback = { viewModel.getCharacters() })
                    binding.recyclerView.isVisible = false
                }
                is State.Loading -> showSpinnerLoading(true)
            }
        })
    }

    private fun setupRecyclerView(isLoad: Boolean = false) {
        characterAdapter = CharacterAdapter()

        binding.recyclerView.apply {
            adapter = characterAdapter
            layoutManager = StaggeredGridLayoutManager(
                resources.getInteger(R.integer.main_columns),
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
        }
        characterAdapter.addLoadStateListener { loadState ->
            handleError(loadState)
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

    private fun setCharacter(characterList: Response<ResponseApi>) {
        if (characterList.body()?.results.isNullOrEmpty()) {
            showSpinnerLoading(false)
            binding.recyclerView.adapter = CharacterAdapter()
        }
    }

    private fun handleError(loadState: CombinedLoadStates) {
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error

        errorState?.let {
            var lay = binding.root
            Snackbar.make(lay, (R.string.error_dialog_retry), Snackbar.LENGTH_LONG)
                .setAction(R.string.ok) {
                    lay.setBackgroundColor(Color.CYAN)
                }.show()
        }
    }

    private fun showSpinnerLoading(loading: Boolean) {
        binding.recyclerView.isVisible = !loading
        binding.progressBar.isVisible = loading
    }

    private fun showErrorDialog(callback: (() -> Unit)? = null) {
        showSpinnerLoading(false)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.error_dialog))
            .setMessage(getString(R.string.error_dialog_detail))
            .setPositiveButton(getString(R.string.try_again)) { _, _ -> viewModel.getCharacters() }
            .setNegativeButton(getString(R.string.ok)) { _, _ -> }
            .setCancelable(false)
            .show()
    }

}