package com.myrickmorty.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.myrickmorty.R
import com.myrickmorty.core.ApiStatus
import com.myrickmorty.databinding.FragmentDetailBinding
import com.myrickmorty.viewmodel.FragmentDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentDetail : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<FragmentDetailArgs>()
    private val viewModel: FragmentDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        viewModel.getCharacters()
        viewModel.newStatus.observe(viewLifecycleOwner, Observer {
            when (it) {
                ApiStatus.LOADING -> {
                    showSpinnerLoading(true)
                }
                ApiStatus.DONE -> {
                    loadData()
                    showSpinnerLoading(false)
                    failedData(true)
                }
                ApiStatus.ERROR -> {
                    failedData(false)
                    showSpinnerLoading(true)
                    val handlerTimer = Handler()
                    handlerTimer.postDelayed(Runnable {
                        showErrorDialog()
                    }, 1000)
                }
            }
        })
    }

    private fun failedData(view: Boolean) {
        binding.cardView.isVisible = view
        binding.tvSpecies.isVisible = view
        binding.tvStatus.isVisible = view
        binding.tvGender.isVisible = view
        binding.dividerLine.isVisible = view
        binding.tvExtra.isVisible = view
        binding.linearLayoutName.isVisible = view
        binding.linearLayoutType.isVisible = view
        binding.linearLayoutId.isVisible = view
        binding.linearLayoutCreated.isVisible = view
        binding.linearLayoutUrl.isVisible = view
    }

    //Load data in fragment detail (imagen and data)
    private fun loadData() {
        Glide.with(requireContext())
            .load(args.image)
            .centerCrop()
            .into(binding.imgCharacter)

        Glide.with(requireContext())
            .load(args.image)
            .circleCrop()
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

    //Show spinner loading
    private fun showSpinnerLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }

    //Show Error Dialog
    private fun showErrorDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.error_dialog))
            .setMessage(getString(R.string.error_dialog_detail))
            .setPositiveButton(getString(R.string.try_again)) { _, _ -> viewModel.getCharacters() }
            //.setNegativeButton(getString(R.string.ok)) { _, _ -> showSpinnerLoading(true) }
            //.setCancelable(false)
            .show()
    }
}