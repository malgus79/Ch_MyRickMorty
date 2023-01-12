package com.myrickmorty.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myrickmorty.R
import com.myrickmorty.core.Resource
import com.myrickmorty.core.hide
import com.myrickmorty.core.show
import com.myrickmorty.core.showToast
import com.myrickmorty.databinding.FragmentSearchBinding
import com.myrickmorty.model.data.RickMorty
import com.myrickmorty.ui.adapter.SearchAdapter
import com.myrickmorty.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), SearchAdapter.OnCharacterClickListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        searchAdapter = SearchAdapter(requireContext(), this)

        setupSearView()
        setupSearchCharacter()

    }

    private fun setupSearchCharacter() {
        viewModel.fetchCharacterList.observe(viewLifecycleOwner, Observer {
            with(binding) {
                when (it) {
                    is Resource.Loading -> {
                        emptyContainer.root.hide()
                        progressBar.show()
                    }
                    is Resource.Success -> {
                        progressBar.hide()
                        if (it.data.results.isEmpty()) {
                            rvCharacterSearch.hide()
                            emptyContainer.root.show()
                            return@Observer
                        }
                        //rvMoviesSearch.show()
                        setupSearchRecyclerView()
                        searchAdapter.setCharacterList(it.data.results)
                    }
                    is Resource.Failure -> {
                        progressBar.hide()
                        showToast(getString(R.string.error_dialog_detail) + it.exception)
                    }
                }
            }
        })
    }

    private fun setupSearView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.setCharacterSearched(query)
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query!!.isNotEmpty()) {
                    viewModel.setCharacterSearched(query)
                } else {
                    binding.rvCharacterSearch.hide()
                }
                return true
            }
        })
    }

    private fun setupSearchRecyclerView() {
        binding.rvCharacterSearch.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            show()
        }
    }

    override fun onCharacterClick(character: RickMorty, position: Int) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(character)
        )
    }
}