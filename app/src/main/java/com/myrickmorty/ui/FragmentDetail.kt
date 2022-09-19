package com.myrickmorty.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.myrickmorty.R
import com.myrickmorty.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentDetail : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<FragmentDetailArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailBinding.bind(view)

        Glide.with(requireContext())
            .load(args.image)
            .centerCrop()
            .into(binding.imgCharacter)

        Glide.with(requireContext())
            .load(args.image)
            .circleCrop()
            .into(binding.imgCharacterSmall)

        binding.txtNameTitle.text = args.name
        binding.txtSpecies.text = args.species
        binding.txtStatus.text = args.status
        binding.txtGender.text = args.gender
        binding.txtType.text = args.type
        binding.txtId.text = args.id.toString()
        binding.txtCreated.text = args.created
        binding.txtUrl.text = args.url

    }
}