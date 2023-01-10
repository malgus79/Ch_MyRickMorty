package com.myrickmorty.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.myrickmorty.R
import com.myrickmorty.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        showDataDetails()

    }

    private fun showDataDetails() {
        Glide.with(requireContext())
            .load(args.image)
            .centerCrop()
            .into(binding.imgCharacter)

        Glide.with(requireContext())
            .load(args.image)
            .centerCrop()
            .into(binding.imgCharacterSmall)

        binding.tvName.text = args.name
        binding.tvSpecies.text = args.species
        binding.tvStatus.text = args.status
        binding.tvGender.text = args.gender
        binding.tvType.text = args.type
        binding.tvId.text = args.id.toString()
        binding.tvCreated.text = args.created
        binding.tvUrl.text = args.url
    }
}