package com.myrickmorty.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.myrickmorty.databinding.CharacterLayoutBinding
import com.myrickmorty.model.data.RickMorty
import com.myrickmorty.ui.fragment.FragmentListDirections

class CharacterAdapter : PagingDataAdapter<RickMorty,
        CharacterAdapter.ImageViewHolder>(diffCallback) {


    inner class ImageViewHolder(
        val binding: CharacterLayoutBinding,
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            CharacterLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currChar = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                tvDescription.text = "${currChar?.name}"

                Glide.with(this)
                    .load(currChar?.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)

                setOnClickListener {
                    val action = FragmentListDirections.actionFragmentListToFragmentDetail(
                        currChar?.gender.toString(),
                        currChar?.id!!,
                        currChar.image.toString(),
                        currChar.name.toString(),
                        currChar.species.toString(),
                        currChar.status.toString(),
                        currChar.type.toString(),
                        currChar.url.toString(),
                        currChar.created.toString()
                    )
                    this.findNavController().navigate(action)
                }

//                val imageLink = currChar?.image
//                imageView.load(imageLink) {
//                    crossfade(true)
//                    crossfade(1000)
//                }
            }
        }
    }
}