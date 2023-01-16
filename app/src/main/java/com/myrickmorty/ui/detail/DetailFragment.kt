package com.myrickmorty.ui.detail

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.myrickmorty.R
import com.myrickmorty.core.utils.showToast
import com.myrickmorty.databinding.FragmentDetailBinding
import com.myrickmorty.data.model.RickMorty
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
        onClickShareCharacter()

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

    private fun onClickShareCharacter() {
        binding.fabShare.setOnClickListener {

            val bitmapDrawable = binding.imgCharacter.drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            val bitmapPath =
                MediaStore.Images.Media.insertImage(context?.contentResolver, bitmap, "IMAGE" + System.currentTimeMillis(), null)
            val bitmapUri = Uri.parse(bitmapPath.toString())
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
            startActivity(Intent.createChooser(intent, "My Rick & Morty"))
        }
    }
}