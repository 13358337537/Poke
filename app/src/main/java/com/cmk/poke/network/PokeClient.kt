package com.cmk.poke.network

import com.cmk.poke.model.PokeResponse
import com.cmk.poke.model.PokemonInfo
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class PokeClient @Inject constructor(
    private val pokeService : PokeService
) {
    suspend fun fetchPokemonList(
        page: Int
    ):ApiResponse<PokeResponse> =
        pokeService.fetchPokemonList(
                limit = PAGING_SIZE,
                offset = page * PAGING_SIZE
        )

    suspend fun fetchPokemonInfo(
        name: String
    ) : ApiResponse<PokemonInfo> =
            pokeService.fetchPokemonInfo(
                    name = name
            )

    companion object {
        private const val PAGING_SIZE = 20
    }
}