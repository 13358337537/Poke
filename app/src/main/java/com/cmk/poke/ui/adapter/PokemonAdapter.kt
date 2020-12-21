package com.cmk.poke.ui.adapter

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cmk.poke.R
import com.cmk.poke.databinding.ItemPokemonBinding
import com.cmk.poke.model.Pokemon
import com.cmk.poke.ui.details.DetailActivity
import com.skydoves.transformationlayout.TransformationCompat.startActivity

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private val items: MutableList<Pokemon> = mutableListOf()
    private var onClickedAt = 0L

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemPokemonBinding>(inflate, R.layout.item_pokemon,parent,false)
        return PokemonViewHolder(binding).apply {
            binding.root.setOnClickListener{
                val position = bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                        ?: return@setOnClickListener
                val currentClickedAt = SystemClock.elapsedRealtime()
                if (currentClickedAt - onClickedAt > binding.transformationLayout.duration) {
                    DetailActivity.startActivity(binding.transformationLayout, items[position])
                    onClickedAt = currentClickedAt
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.binding.apply {
            pokemon = items[position]
            executePendingBindings()
        }
    }

    override fun getItemCount() = items.size

    fun setPokemonList(pokemonList: List<Pokemon>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(pokemonList)
        notifyItemRangeChanged(previousItemSize, pokemonList.size)
    }

    class PokemonViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)

}