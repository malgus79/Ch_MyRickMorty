package com.myrickmorty.ui.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.myrickmorty.R
import com.myrickmorty.core.Resource
import com.myrickmorty.core.common.hide
import com.myrickmorty.core.common.show
import com.myrickmorty.databinding.FragmentFavoriteBinding
import com.myrickmorty.model.data.RickMorty
import com.myrickmorty.ui.adapter.FavoriteAdapter
import com.myrickmorty.viewmodel.fragment.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite),
    FavoriteAdapter.OnCharacterClickListener {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)
        favoriteAdapter = FavoriteAdapter(requireContext(), this)

        setupFavoriteCharacters()
    }

    private fun setupFavoriteCharacters() {
        viewModel.fetchFavoriteCharacters().observe(viewLifecycleOwner, Observer {
            with(binding) {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        if (it.data.results.isEmpty()) {
                            binding.rvFavoriteCharacters.adapter?.notifyDataSetChanged()
                            rvFavoriteCharacters.hide()
                            emptyContainer.root.show()
                            return@Observer
                        }
                        favoriteAdapter.setCharacterList(it.data.results)
                        setupFavoriteRecyclerView()
                    }
                    is Resource.Failure -> {
                        progressBar.hide()
                        Log.d(TAG, "Error: " + it.exception)
                    }
                }
            }
        })
    }

    private fun setupFavoriteRecyclerView() {
        binding.rvFavoriteCharacters.apply {
            adapter = favoriteAdapter
            layoutManager = StaggeredGridLayoutManager(
                resources.getInteger(R.integer.main_columns_favorite),
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
        }
    }

    override fun onCharacterClick(character: RickMorty, position: Int) {
        findNavController().navigate(
            FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(character)
        )
    }

    override fun onCharacterLongClick(character: RickMorty, position: Int) {
        viewModel.deleteFavoriteCharacter(character)
        setupFavoriteCharacters()
        Toast.makeText(requireContext(), R.string.removed_character, Toast.LENGTH_SHORT).show()
    }
}