package com.myrickmorty.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.myrickmorty.R
import com.myrickmorty.databinding.FragmentFavoriteBinding
import com.myrickmorty.model.data.RickMorty
import com.myrickmorty.ui.adapter.FavoriteAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite),
    FavoriteAdapter.OnCharacterClickListener {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)
        favoriteAdapter = FavoriteAdapter(requireContext(), this)
    }

    override fun onCharacterClick(character: RickMorty, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCharacterLongClick(character: RickMorty, position: Int) {
        TODO("Not yet implemented")
    }
}