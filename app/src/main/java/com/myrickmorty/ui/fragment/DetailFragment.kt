package com.myrickmorty.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.myrickmorty.R
import com.myrickmorty.core.showToast
import com.myrickmorty.databinding.FragmentDetailBinding
import com.myrickmorty.model.data.RickMorty
import com.myrickmorty.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var character: RickMorty
    private var isCharacterFavorited: Boolean? = null
    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        requireArguments().let {
            DetailFragmentArgs.fromBundle(it).also { args ->
                character = args.character
            }
        }

        showDataDetails()
        isCharacterFavorited()
        updateButtonIcon()

    }

    private fun showDataDetails() {
        Glide.with(requireContext())
            .load(character.image)
            .centerCrop()
            .into(binding.imgCharacter)

        with(binding) {
            tvName.text = character.name
            tvSpecies.text = character.species
            tvStatus.text = character.status
            tvGender.text = character.gender
            tvType.text = character.type
            tvId.text = character.id.toString()
            tvCreated.text = character.created
            tvUrl.text = character.url
        }
    }

    private fun isCharacterFavorited() {
        binding.fabFavorite.setOnClickListener {
            val isCharacterFavorited = isCharacterFavorited ?: return@setOnClickListener

            if (isCharacterFavorited) {
                showToast(getString(R.string.removed_character))
            } else {
                showToast(getString(R.string.added_character))
            }

            viewModel.saveOrDeleteFavoriteCharacter(character)
            this.isCharacterFavorited = !isCharacterFavorited
            updateButtonIcon()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            isCharacterFavorited = viewModel.isCharacterFavorite(character)
            updateButtonIcon()
        }
    }

    private fun updateButtonIcon() {
        isCharacterFavorited = isCharacterFavorited ?: return

        binding.fabFavorite.setImageResource(
            when {
                isCharacterFavorited!! -> R.drawable.ic_check
                else -> R.drawable.ic_add
            }
        )
    }
}