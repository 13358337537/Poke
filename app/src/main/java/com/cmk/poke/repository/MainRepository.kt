package com.cmk.poke.repository

import androidx.annotation.WorkerThread
import com.cmk.poke.mapper.ErrorResponseMapper
import com.cmk.poke.network.PokeClient
import com.cmk.poke.persistence.PokeDao
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.map
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(
        private val pokeClient : PokeClient,
        private val pokeDao : PokeDao
) : Repository {

    @WorkerThread
    suspend fun fetchPokemonList(
        page : Int,
        onSuccess : () -> Unit,
        onError:(String?) -> Unit
    ) = flow {
        var pokemons = pokeDao.getPokemonList(page)
        if (pokemons.isEmpty()){
            /**
             * fetches a list of [Pokemon] from the network and getting [ApiResponse] asynchronously.
             * @see [suspendOnSuccess](https://github.com/skydoves/sandwich#suspendonsuccess-suspendonerror-suspendonexception)
             */
            val response = pokeClient.fetchPokemonList(page = page)
            response.suspendOnSuccess {
                data.whatIfNotNull { response ->
                    pokemons = response.results
                    pokemons.forEach { pokemon -> pokemon.page = page }
                    pokeDao.insertPokemonList(pokemons)
                    emit(pokeDao.getAllPokemonList(page))
                    onSuccess()
                }
            }.onError {
                /** maps the [ApiResponse.Failure.Error] to the [PokemonErrorResponse] using the mapper. */
                map(ErrorResponseMapper) {
                    onError("[Code: $code]: $message")
                }
            }.onException {
                onError(message)
            }
        } else {
            emit(pokeDao.getAllPokemonList(page))
            onSuccess()
        }
    }.flowOn(Dispatchers.IO)
}