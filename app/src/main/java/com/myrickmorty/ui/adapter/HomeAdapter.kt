package com.myrickmorty.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.myrickmorty.databinding.ItemCharacterBinding
import com.myrickmorty.model.data.RickMorty
import com.myrickmorty.ui.fragment.HomeFragmentDirections

class HomeAdapter : PagingDataAdapter<RickMorty,
        HomeAdapter.HomeViewHolder>(diffCallback) {

    inner class HomeViewHolder(
        val binding: ItemCharacterBinding,
    ) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<RickMorty>() {
            override fun areItemsTheSame(oldItem: RickMorty, newItem: RickMorty): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RickMorty, newItem: RickMorty): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currChar = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                tvDescription.text = "${currChar?.name}"

                Glide.with(this)
                    .load(currChar?.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)

                setOnClickListener {
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        currChar!!
                    )
                    this.findNavController().navigate(action)
                }
            }
        }
    }
}