package com.myrickmorty.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.myrickmorty.R
import com.myrickmorty.core.BaseViewHolder
import com.myrickmorty.databinding.ItemFavoriteBinding
import com.myrickmorty.model.data.RickMorty

class FavoriteAdapter(
    private val context: Context,
    private val itemClickListener: OnCharacterClickListener
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var characterList = listOf<RickMorty>()

    interface OnCharacterClickListener {
        fun onCharacterClick(character: RickMorty, position: Int)
        fun onCharacterLongClick(character: RickMorty, position: Int)
    }

    fun setCharacterList(characterList: List<RickMorty>) {
        this.characterList = characterList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemFavoriteBinding.inflate(LayoutInflater.from(context), parent, false)
//        return MainViewHolder(itemBinding)

        val holder = MainViewHolder(itemBinding)

        holder.itemView.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener

            itemClickListener.onCharacterClick(characterList[position], position)
        }

        holder.itemView.setOnLongClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnLongClickListener true

            itemClickListener.onCharacterLongClick(characterList[position], position)

            return@setOnLongClickListener true
        }

        return holder
    }

    override fun getItemCount(): Int = characterList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(characterList[position], position)
        }
    }

    private inner class MainViewHolder(private val binding: ItemFavoriteBinding) :
        BaseViewHolder<RickMorty>(binding.root) {

        override fun bind(item: RickMorty, position: Int): Unit = with(binding) {
            Glide.with(context)
                .load(item.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.gradient)
                .centerCrop()
                .into(imgCharacter)
        }
    }
}