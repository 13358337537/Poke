package com.cmk.poke.network

import com.cmk.poke.model.PokeResponse
import com.cmk.poke.model.PokemonInfo
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeService {

    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit : Int = 20,
        @Query("offset") offset : Int = 0
    ) : ApiResponse<PokeResponse>

    @GET("pokemon/{name}")
    suspend fun fetchPokemonInfo(@Path("name") name : String): ApiResponse<PokemonInfo>
}