package com.cmk.poke.binding

import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.cmk.poke.model.Pokemon
import com.cmk.poke.ui.adapter.PokemonAdapter
import com.cmk.poke.ui.main.MainViewModel
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.skydoves.whatif.whatIfNotNullAs
import com.skydoves.whatif.whatIfNotNullOrEmpty

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("adapter")
    fun bindAdapter(view: RecyclerView,adapter: RecyclerView.Adapter<*>) {
        view.adapter = adapter.apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    @JvmStatic
    @BindingAdapter("paginationPokemonList")
    fun paginationPokemonList(view : RecyclerView,viewModel: MainViewModel) {
        RecyclerViewPaginator(
            recyclerView = view,
            isLoading = { viewModel.isLoading.get() },
            loadMore = { viewModel.fetchPokemonList() },
            onLast = {false}
        ).run {
            threshold = 8
        }
    }

    @JvmStatic
    @BindingAdapter("adapterPokemonList")
    fun bindAdapterPokemonList(view: RecyclerView, pokemonList: List<Pokemon>?) {
        pokemonList.whatIfNotNullOrEmpty { itemList ->
            view.adapter.whatIfNotNullAs<PokemonAdapter> { adapter ->
                adapter.setPokemonList(itemList)
            }
        }
    }
}